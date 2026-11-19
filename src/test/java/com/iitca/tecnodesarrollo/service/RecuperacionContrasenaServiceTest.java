package com.iitca.tecnodesarrollo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.iitca.tecnodesarrollo.dto.RecuperacionContrasena;
import com.iitca.tecnodesarrollo.dto.Usuarios;
import com.iitca.tecnodesarrollo.repo.RecuperacionContrasenaRepo;
import com.iitca.tecnodesarrollo.repo.UsuariosRepo;
import com.iitca.tecnodesarrollo.service.RecuperacionContrasenaService.ResultadoCambio;

@ExtendWith(MockitoExtension.class)
class RecuperacionContrasenaServiceTest {

    private static final String CORREO = "usuario@ejemplo.com";

    @Mock RecuperacionContrasenaRepo recuperacionRepo;
    @Mock UsuariosRepo usuariosRepo;
    @Mock CorreoService correoService;
    @Spy PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
    @InjectMocks RecuperacionContrasenaService service;

    private Usuarios usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuarios();
        usuario.setId_us(7);
        usuario.setUs_correo(CORREO);
        usuario.setUs_contrasena(passwordEncoder.encode("anterior123"));
    }

    private RecuperacionContrasena filaConCodigo(String codigo) {
        RecuperacionContrasena rc = new RecuperacionContrasena();
        rc.setUsuarioId(7);
        rc.setCodigoHash(RecuperacionContrasenaService.sha256Hex(codigo));
        rc.setCodigoExpiraEn(LocalDateTime.now().plusMinutes(15));
        rc.setCreadoEn(LocalDateTime.now());
        return rc;
    }

    @Test
    void solicitarConCorreoInexistenteNoCreaFilaNiEnviaCorreo() {
        when(usuariosRepo.findByUs_correo(CORREO)).thenReturn(Optional.empty());
        service.solicitar(CORREO);
        verify(recuperacionRepo, never()).save(any());
        verifyNoInteractions(correoService);
    }

    @Test
    void solicitarGuardaSoloElHashYEnviaElCodigo() {
        when(usuariosRepo.findByUs_correo(CORREO)).thenReturn(Optional.of(usuario));
        when(recuperacionRepo.countByUsuarioIdAndCreadoEnAfter(eq(7), any())).thenReturn(0L);

        service.solicitar("  " + CORREO + " ");

        ArgumentCaptor<RecuperacionContrasena> fila = ArgumentCaptor.forClass(RecuperacionContrasena.class);
        ArgumentCaptor<String> codigo = ArgumentCaptor.forClass(String.class);
        verify(recuperacionRepo).invalidarActivas(7);
        verify(recuperacionRepo).save(fila.capture());
        verify(correoService).enviarCodigoRecuperacion(eq(CORREO), codigo.capture());
        assertTrue(codigo.getValue().matches("\\d{6}"));
        assertEquals(RecuperacionContrasenaService.sha256Hex(codigo.getValue()), fila.getValue().getCodigoHash());
    }

    @Test
    void solicitarRespetaElLimiteDeFrecuencia() {
        when(usuariosRepo.findByUs_correo(CORREO)).thenReturn(Optional.of(usuario));
        when(recuperacionRepo.countByUsuarioIdAndCreadoEnAfter(eq(7), any())).thenReturn(1L);
        service.solicitar(CORREO);
        verify(recuperacionRepo, never()).save(any());
        verifyNoInteractions(correoService);
    }

    @Test
    void verificarConCodigoCorrectoDevuelveTokenUnaSolaVez() {
        RecuperacionContrasena rc = filaConCodigo("123456");
        when(usuariosRepo.findByUs_correo(CORREO)).thenReturn(Optional.of(usuario));
        when(recuperacionRepo.findTopByUsuarioIdAndUsadoFalseOrderByCreadoEnDesc(7)).thenReturn(Optional.of(rc));

        Optional<String> token = service.verificar(CORREO, "123456");
        assertTrue(token.isPresent());
        assertEquals(RecuperacionContrasenaService.sha256Hex(token.get()), rc.getTokenHash());

        // El mismo código no emite un segundo token
        assertTrue(service.verificar(CORREO, "123456").isEmpty());
    }

    @Test
    void verificarBloqueaTrasCincoIntentosFallidos() {
        RecuperacionContrasena rc = filaConCodigo("123456");
        when(usuariosRepo.findByUs_correo(CORREO)).thenReturn(Optional.of(usuario));
        when(recuperacionRepo.findTopByUsuarioIdAndUsadoFalseOrderByCreadoEnDesc(7)).thenReturn(Optional.of(rc));

        for (int i = 0; i < 5; i++) {
            assertTrue(service.verificar(CORREO, "000000").isEmpty());
        }
        assertEquals(5, rc.getIntentos());
        assertTrue(rc.isUsado());
        assertTrue(service.verificar(CORREO, "123456").isEmpty());
    }

    @Test
    void verificarRechazaCodigoExpirado() {
        RecuperacionContrasena rc = filaConCodigo("123456");
        rc.setCodigoExpiraEn(LocalDateTime.now().minusSeconds(1));
        when(usuariosRepo.findByUs_correo(CORREO)).thenReturn(Optional.of(usuario));
        when(recuperacionRepo.findTopByUsuarioIdAndUsadoFalseOrderByCreadoEnDesc(7)).thenReturn(Optional.of(rc));
        assertTrue(service.verificar(CORREO, "123456").isEmpty());
    }

    @Test
    void cambiarRechazaContrasenaDebil() {
        assertEquals(ResultadoCambio.CONTRASENA_INVALIDA, service.cambiar("tok", "corta1"));
        assertEquals(ResultadoCambio.CONTRASENA_INVALIDA, service.cambiar("tok", "sololetras"));
        assertEquals(ResultadoCambio.CONTRASENA_INVALIDA, service.cambiar("tok", "12345678"));
        verifyNoInteractions(recuperacionRepo);
    }

    @Test
    void cambiarConTokenValidoActualizaLaContrasenaYConsumeElToken() {
        RecuperacionContrasena rc = filaConCodigo("123456");
        rc.setTokenHash(RecuperacionContrasenaService.sha256Hex("tokenX"));
        rc.setTokenExpiraEn(LocalDateTime.now().plusMinutes(10));
        when(recuperacionRepo.findByTokenHashAndUsadoFalse(rc.getTokenHash())).thenReturn(Optional.of(rc));
        when(usuariosRepo.findById(7)).thenReturn(Optional.of(usuario));

        assertEquals(ResultadoCambio.OK, service.cambiar("tokenX", "NuevaClave9"));
        assertTrue(rc.isUsado());
        assertTrue(passwordEncoder.matches("NuevaClave9", usuario.getUs_contrasena()));
        assertFalse(passwordEncoder.matches("anterior123", usuario.getUs_contrasena()));
        verify(recuperacionRepo).invalidarActivas(7);
        verify(correoService).enviarAvisoCambio(CORREO);
    }

    @Test
    void cambiarRechazaTokenExpirado() {
        RecuperacionContrasena rc = filaConCodigo("123456");
        rc.setTokenHash(RecuperacionContrasenaService.sha256Hex("tokenX"));
        rc.setTokenExpiraEn(LocalDateTime.now().minusSeconds(1));
        when(recuperacionRepo.findByTokenHashAndUsadoFalse(rc.getTokenHash())).thenReturn(Optional.of(rc));

        assertEquals(ResultadoCambio.TOKEN_INVALIDO, service.cambiar("tokenX", "NuevaClave9"));
        verify(usuariosRepo, never()).save(any());
    }
}
