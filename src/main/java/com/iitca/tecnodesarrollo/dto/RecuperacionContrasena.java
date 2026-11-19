package com.iitca.tecnodesarrollo.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

/**
 * Solicitud de recuperación de contraseña. El código y el token solo se guardan
 * como hash SHA-256. usuarioId es una columna simple (sin FK) para no bloquear
 * el borrado de usuarios.
 */
@Entity
@Table(name = "recuperacion_contrasena", indexes = {
    @Index(name = "idx_rc_usuario", columnList = "usuario_id"),
    @Index(name = "idx_rc_token", columnList = "token_hash")
})
public class RecuperacionContrasena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "codigo_hash", nullable = false, length = 64)
    private String codigoHash;

    @Column(name = "codigo_expira_en", nullable = false)
    private LocalDateTime codigoExpiraEn;

    @Column(name = "intentos", nullable = false)
    private int intentos;

    @Column(name = "token_hash", length = 64)
    private String tokenHash;

    @Column(name = "token_expira_en")
    private LocalDateTime tokenExpiraEn;

    @Column(name = "usado", nullable = false)
    private boolean usado;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public String getCodigoHash() { return codigoHash; }
    public void setCodigoHash(String codigoHash) { this.codigoHash = codigoHash; }

    public LocalDateTime getCodigoExpiraEn() { return codigoExpiraEn; }
    public void setCodigoExpiraEn(LocalDateTime codigoExpiraEn) { this.codigoExpiraEn = codigoExpiraEn; }

    public int getIntentos() { return intentos; }
    public void setIntentos(int intentos) { this.intentos = intentos; }

    public String getTokenHash() { return tokenHash; }
    public void setTokenHash(String tokenHash) { this.tokenHash = tokenHash; }

    public LocalDateTime getTokenExpiraEn() { return tokenExpiraEn; }
    public void setTokenExpiraEn(LocalDateTime tokenExpiraEn) { this.tokenExpiraEn = tokenExpiraEn; }

    public boolean isUsado() { return usado; }
    public void setUsado(boolean usado) { this.usado = usado; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}
