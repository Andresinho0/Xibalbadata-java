package com.iitca.tecnodesarrollo.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Envío de correos mediante la API HTTP de Resend (Railway bloquea SMTP en
 * planes no Pro). Los métodos son @Async para que el tiempo de respuesta de
 * los endpoints no revele si un correo está registrado.
 */
@Service
public class CorreoService {

    private static final Logger log = LoggerFactory.getLogger(CorreoService.class);

    private final RestClient restClient = RestClient.create("https://api.resend.com");

    @Value("${resend.api-key:}")
    private String apiKey;

    @Value("${resend.from:onboarding@resend.dev}")
    private String from;

    @Async
    public void enviarCodigoRecuperacion(String correo, String codigo) {
        String html = "<p>Recibimos una solicitud para restablecer tu contraseña de Xibalba Data.</p>"
                + "<p>Tu código de verificación es:</p>"
                + "<p style=\"font-size:28px;font-weight:bold;letter-spacing:6px\">" + codigo + "</p>"
                + "<p>El código vence en 15 minutos. Si no solicitaste este cambio, ignora este correo.</p>";
        enviar(correo, "Código de recuperación de contraseña", html);
    }

    @Async
    public void enviarAvisoCambio(String correo) {
        String html = "<p>Tu contraseña de Xibalba Data fue cambiada correctamente.</p>"
                + "<p>Si no fuiste tú, contacta de inmediato al administrador del sistema.</p>";
        enviar(correo, "Tu contraseña fue cambiada", html);
    }

    private void enviar(String para, String asunto, String html) {
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("RESEND_API_KEY no configurada: no se envió el correo '{}'", asunto);
            return;
        }
        try {
            restClient.post()
                    .uri("/emails")
                    .header("Authorization", "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("from", from, "to", List.of(para), "subject", asunto, "html", html))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            // No se registra el contenido del correo (contiene el código)
            log.error("Error al enviar el correo '{}': {}", asunto, e.getMessage());
        }
    }
}
