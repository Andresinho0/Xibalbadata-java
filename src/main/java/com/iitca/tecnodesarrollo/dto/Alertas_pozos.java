package com.iitca.tecnodesarrollo.dto;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alertas_pozos")
public class Alertas_pozos {

	@Id
	@Column(name="Id_al")
	private int id_al;
	@Column(name = "Id_lp")
	private int idLp;
	@Column(name="Al_clave_de_pozo")
	private String al_clave_de_pozo;
	@Column(name = "Al_tipo_de_alerta")
	private String al_tipo_de_alerta;
	@Column(name="Al_comentarios", columnDefinition="TEXT")
	private String al_comentarios;
	@Column(name = "Al_operador")
	private String al_operador;
	@CreationTimestamp
	@Column(name="Al_fechacap")
	private LocalDateTime al_fechacap;

    // --- Cierre / historial --- //

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = EstadoRegistro.PENDIENTE;

    @Column(name = "completada_en")
    private LocalDateTime completada_en;

    @Column(name = "completada_por")
    private Integer completada_por;

    @Column(name = "notas_cierre", columnDefinition = "TEXT")
    private String notas_cierre;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getCompletada_en() {
        return completada_en;
    }

    public void setCompletada_en(LocalDateTime completada_en) {
        this.completada_en = completada_en;
    }

    public Integer getCompletada_por() {
        return completada_por;
    }

    public void setCompletada_por(Integer completada_por) {
        this.completada_por = completada_por;
    }

    public String getNotas_cierre() {
        return notas_cierre;
    }

    public void setNotas_cierre(String notas_cierre) {
        this.notas_cierre = notas_cierre;
    }

    public int getId_al() {
        return id_al;
    }

    public void setId_al(int id_al) {
        this.id_al = id_al;
    }

    public int getIdLp() {
        return idLp;
    }

    public void setIdLp(int idLp) {
        this.idLp = idLp;
    }

    public String getAl_clave_de_pozo() {
        return al_clave_de_pozo;
    }

    public void setAl_clave_de_pozo(String al_clave_de_pozo) {
        this.al_clave_de_pozo = al_clave_de_pozo;
    }

    public String getAl_tipo_de_alerta() {
        return al_tipo_de_alerta;
    }

    public void setAl_tipo_de_alerta(String al_tipo_de_alerta) {
        this.al_tipo_de_alerta = al_tipo_de_alerta;
    }

    public String getAl_comentarios() {
        return al_comentarios;
    }

    public void setAl_comentarios(String al_comentarios) {
        this.al_comentarios = al_comentarios;
    }

    public String getAl_operador() {
        return al_operador;
    }

    public void setAl_operador(String al_operador) {
        this.al_operador = al_operador;
    }

    public LocalDateTime getAl_fechacap() {
        return al_fechacap;
    }

    public void setAl_fechacap(LocalDateTime al_fechacap) {
        this.al_fechacap = al_fechacap;
    }

}