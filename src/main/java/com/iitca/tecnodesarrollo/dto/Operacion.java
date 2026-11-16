package com.iitca.tecnodesarrollo.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "operacion")
public class Operacion {

    @Id
    @Column(name="op_id")
    private int id_op;

    @Column(name = "Id_lp")
    private int idLp;

    @Column(name="op_cpozo")
    private String op_cpozo;

    @CreationTimestamp
    @Column(name="op_fecha_captura")
    private LocalDateTime op_fecha_captura; 

    @Column(name = "op_operador")
    private String op_operador;

    // Cambiado de float a Double para prevenir errores NullPointerException
    @Column(name = "op_nestatico")
    private Double op_nestatico;

    @Column(name="op_ndinamico")
    private Double op_ndinamico;

    @Column(name="op_gasto")
    private Double op_gasto;

    @Column(name="op_presion")
    private Double op_presion;

    @Column(name="op_tiempo_op")
    private Double op_tiempo_op;

    @Column(name="op_observaciones")
    private String op_observaciones;

    @Column (name="op_operando")
    private String op_operando;

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


    public int getId_op() {
        return id_op;
    }

    public void setId_op(int id_op) {
        this.id_op = id_op;
    }

    public int getIdLp() {
        return idLp;
    }

    public void setIdLp(int idLp) {
        this.idLp = idLp;
    }

    public String getOp_cpozo() {
        return op_cpozo;
    }

    public void setOp_cpozo(String op_cpozo) {
        this.op_cpozo = op_cpozo;
    }

    public LocalDateTime getOp_fecha_captura() {
        return op_fecha_captura;
    }

    public void setOp_fecha_captura(LocalDateTime op_fecha_captura) {
        this.op_fecha_captura = op_fecha_captura;
    }

    public String getOp_operador() {
        return op_operador;
    }

    public void setOp_operador(String op_operador) {
        this.op_operador = op_operador;
    }

    public Double getOp_nestatico() {
        return op_nestatico;
    }

    public void setOp_nestatico(Double op_nestatico) {
        this.op_nestatico = op_nestatico;
    }

    public Double getOp_ndinamico() {
        return op_ndinamico;
    }

    public void setOp_ndinamico(Double op_ndinamico) {
        this.op_ndinamico = op_ndinamico;
    }

    public Double getOp_gasto() {
        return op_gasto;
    }

    public void setOp_gasto(Double op_gasto) {
        this.op_gasto = op_gasto;
    }

    public Double getOp_presion() {
        return op_presion;
    }

    public void setOp_presion(Double op_presion) {
        this.op_presion = op_presion;
    }

    public Double getOp_tiempo_op() {
        return op_tiempo_op;
    }

    public void setOp_tiempo_op(Double op_tiempo_op) {
        this.op_tiempo_op = op_tiempo_op;
    }

    public String getOp_observaciones() {
        return op_observaciones;
    }

    public void setOp_observaciones(String op_observaciones) {
        this.op_observaciones = op_observaciones;
    }

    public String getOp_operando() {
        return op_operando;
    }

    public void setOp_operando(String op_operando) {
        this.op_operando = op_operando;
    }
    
}