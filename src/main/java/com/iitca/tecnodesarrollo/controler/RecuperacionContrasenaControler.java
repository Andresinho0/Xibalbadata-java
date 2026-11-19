package com.iitca.tecnodesarrollo.controler;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iitca.tecnodesarrollo.dto.CambiarContrasenaRequest;
import com.iitca.tecnodesarrollo.dto.SolicitarRecuperacionRequest;
import com.iitca.tecnodesarrollo.dto.VerificarCodigoRequest;
import com.iitca.tecnodesarrollo.service.RecuperacionContrasenaService;
import com.iitca.tecnodesarrollo.service.RecuperacionContrasenaService.ResultadoCambio;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/recuperar")
public class RecuperacionContrasenaControler {

    @Autowired
    private RecuperacionContrasenaService recuperacionService;

    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitar(@RequestBody SolicitarRecuperacionRequest request) {
        recuperacionService.solicitar(request.getCorreo());
        // Misma respuesta exista o no el correo
        return ResponseEntity.ok(Map.of("mensaje", "Si el correo está registrado, recibirás un código."));
    }

    @PostMapping("/verificar")
    public ResponseEntity<?> verificar(@RequestBody VerificarCodigoRequest request) {
        Optional<String> token = recuperacionService.verificar(request.getCorreo(), request.getCodigo());
        if (token.isPresent()) {
            return ResponseEntity.ok(Map.of("token", token.get()));
        }
        return ResponseEntity.badRequest().body(Map.of("mensaje", "Código inválido o expirado"));
    }

    @PostMapping("/cambiar")
    public ResponseEntity<?> cambiar(@RequestBody CambiarContrasenaRequest request) {
        ResultadoCambio resultado = recuperacionService.cambiar(request.getToken(), request.getNuevaContrasena());
        return switch (resultado) {
            case OK -> ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada"));
            case CONTRASENA_INVALIDA -> ResponseEntity.badRequest().body(Map.of("mensaje",
                    "La contraseña debe tener al menos 8 caracteres, una letra y un número"));
            case TOKEN_INVALIDO -> ResponseEntity.badRequest().body(Map.of("mensaje", "Enlace inválido o expirado"));
        };
    }
}
