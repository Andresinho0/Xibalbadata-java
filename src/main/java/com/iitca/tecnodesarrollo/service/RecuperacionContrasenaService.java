package com.iitca.tecnodesarrollo.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iitca.tecnodesarrollo.dto.RecuperacionContrasena;
import com.iitca.tecnodesarrollo.dto.Usuarios;
import com.iitca.tecnodesarrollo.repo.RecuperacionContrasenaRepo;
import com.iitca.tecnodesarrollo.repo.UsuariosRepo;

/**
 * Recuperación de contraseña en 3 pasos: solicitar código -> verificar código
 * (devuelve token) -> cambiar contraseña con el token.
 *
 * Los métodos devuelven resultados en lugar de lanzar excepciones para que los
 * cambios (por ejemplo, el contador de intentos fallidos) se confirmen siempre.
 * Nunca se registran el código, el token ni la contraseña.
 */
@Service
public class RecuperacionContrasenaService {

    static final int MINUTOS_CODIGO = 15;
    static final int MINUTOS_TOKEN = 10;
    static final int MAX_INTENTOS = 5;
    static final int SEGUNDOS_ENTRE_SOLICITUDES = 60;
    static final int MAX_SOLICITUDES_POR_HORA = 5;

    public enum ResultadoCambio { OK, CONTRASENA_INVALIDA, TOKEN_INVALIDO }

    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    private RecuperacionContrasenaRepo recuperacionRepo;

    @Autowired
    private UsuariosRepo usuariosRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CorreoService correoService;

    // PASO 1: SOLICITAR CÓDIGO (la respuesta al cliente es siempre la misma)
    @Transactional
    public void solicitar(String correo) {
        Optional<Usuarios> usuarioOpt = buscarUsuario(correo);
        if (usuarioOpt.isEmpty()) {
            return;
        }
        Usuarios usuario = usuarioOpt.get();
        Integer usuarioId = usuario.getId_us();
        LocalDateTime ahora = LocalDateTime.now();

        // Límite simple: 1 solicitud por minuto y 5 por hora por usuario
        if (recuperacionRepo.countByUsuarioIdAndCreadoEnAfter(usuarioId, ahora.minusSeconds(SEGUNDOS_ENTRE_SOLICITUDES)) > 0
                || recuperacionRepo.countByUsuarioIdAndCreadoEnAfter(usuarioId, ahora.minusHours(1)) >= MAX_SOLICITUDES_POR_HORA) {
            return;
        }

        // Solo vale el último código
        recuperacionRepo.invalidarActivas(usuarioId);

        String codigo = generarCodigo();
        RecuperacionContrasena rc = new RecuperacionContrasena();
        rc.setUsuarioId(usuarioId);
        rc.setCodigoHash(sha256Hex(codigo));
        rc.setCodigoExpiraEn(ahora.plusMinutes(MINUTOS_CODIGO));
        rc.setIntentos(0);
        rc.setUsado(false);
        rc.setCreadoEn(ahora);
        recuperacionRepo.save(rc);

        correoService.enviarCodigoRecuperacion(usuario.getUs_correo(), codigo);
    }

    // PASO 2: VERIFICAR CÓDIGO -> token en claro si es correcto
    @Transactional
    public Optional<String> verificar(String correo, String codigo) {
        Optional<Usuarios> usuarioOpt = buscarUsuario(correo);
        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }

        Optional<RecuperacionContrasena> rcOpt =
                recuperacionRepo.findTopByUsuarioIdAndUsadoFalseOrderByCreadoEnDesc(usuarioOpt.get().getId_us());
        if (rcOpt.isEmpty()) {
            return Optional.empty();
        }
        RecuperacionContrasena rc = rcOpt.get();
        LocalDateTime ahora = LocalDateTime.now();

        // Expirado, sin intentos o ya verificado (un código no emite dos tokens)
        if (rc.getCodigoExpiraEn().isBefore(ahora)
                || rc.getIntentos() >= MAX_INTENTOS
                || rc.getTokenHash() != null) {
            return Optional.empty();
        }

        String codigoLimpio = codigo == null ? "" : codigo.trim();
        if (!hashIguales(sha256Hex(codigoLimpio), rc.getCodigoHash())) {
            rc.setIntentos(rc.getIntentos() + 1);
            if (rc.getIntentos() >= MAX_INTENTOS) {
                rc.setUsado(true);
            }
            recuperacionRepo.save(rc);
            return Optional.empty();
        }

        String token = generarToken();
        rc.setTokenHash(sha256Hex(token));
        rc.setTokenExpiraEn(ahora.plusMinutes(MINUTOS_TOKEN));
        recuperacionRepo.save(rc);
        return Optional.of(token);
    }

    // PASO 3: CAMBIAR CONTRASEÑA CON EL TOKEN
    @Transactional
    public ResultadoCambio cambiar(String token, String nuevaContrasena) {
        if (!contrasenaValida(nuevaContrasena)) {
            return ResultadoCambio.CONTRASENA_INVALIDA;
        }
        if (token == null || token.isBlank()) {
            return ResultadoCambio.TOKEN_INVALIDO;
        }

        Optional<RecuperacionContrasena> rcOpt = recuperacionRepo.findByTokenHashAndUsadoFalse(sha256Hex(token.trim()));
        if (rcOpt.isEmpty()
                || rcOpt.get().getTokenExpiraEn() == null
                || rcOpt.get().getTokenExpiraEn().isBefore(LocalDateTime.now())) {
            return ResultadoCambio.TOKEN_INVALIDO;
        }
        RecuperacionContrasena rc = rcOpt.get();

        Optional<Usuarios> usuarioOpt = usuariosRepo.findById(rc.getUsuarioId());
        if (usuarioOpt.isEmpty()) {
            return ResultadoCambio.TOKEN_INVALIDO;
        }
        Usuarios usuario = usuarioOpt.get();
        usuario.setUs_contrasena(passwordEncoder.encode(nuevaContrasena));
        usuariosRepo.save(usuario);

        // Invalida esta y cualquier otra solicitud activa del usuario
        rc.setUsado(true);
        recuperacionRepo.save(rc);
        recuperacionRepo.invalidarActivas(usuario.getId_us());

        correoService.enviarAvisoCambio(usuario.getUs_correo());
        return ResultadoCambio.OK;
    }

    // ---------- Utilidades ----------

    /** Mínimo 8 caracteres, al menos una letra y un número. */
    static boolean contrasenaValida(String contrasena) {
        return contrasena != null
                && contrasena.length() >= 8
                && contrasena.chars().anyMatch(Character::isLetter)
                && contrasena.chars().anyMatch(Character::isDigit);
    }

    private Optional<Usuarios> buscarUsuario(String correo) {
        if (correo == null || correo.isBlank()) {
            return Optional.empty();
        }
        return usuariosRepo.findByUs_correo(correo.trim());
    }

    static String generarCodigo() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }

    static String generarToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    static String sha256Hex(String valor) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(valor.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no disponible", e);
        }
    }

    private static boolean hashIguales(String a, String b) {
        return MessageDigest.isEqual(
                a.getBytes(StandardCharsets.UTF_8),
                b.getBytes(StandardCharsets.UTF_8));
    }
}
