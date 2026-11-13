
package com.iitca.tecnodesarrollo.dto;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "mantenimiento")
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id_lp")
    private Integer idLp;

    @Column(name="mtto_operador")
    private Integer mtto_operador;

    @Column(name="mtto_clave_pozo")
    private String mtto_clave_pozo;

    @Column(name="mtto_fecha_captura")
    private LocalDateTime mtto_fecha_captura; 

    @Column(name = "mtto_motor_tipo")
    private String mtto_motor_tipo;

    @Column(name="mtto_motor_hp")
    private Integer mtto_motor_hp;

    @Column(name="mtto_motor_kw")
    private Integer mtto_motor_kw;

    @Column(name="mtto_motor_eficiencia")
    private Double mtto_motor_eficiencia;

    @Column(name="mtto_tablero_tipo")
    private String mtto_tablero_tipo;

    @Column(name="mtto_tablero_capacidad")
    private Integer mtto_tablero_capacidad;

    @Column(name="mtto_transformador_tipo")
    private String mtto_transformador_tipo;

    @Column(name="mtto_transformador_capacidad")
    private Integer mtto_transformador_capacidad;

    @Column(name="mtto_calibre_sumergible")
    private String mtto_calibre_sumergible;

    @Column(name="mtto_longitud_sumergible")
    private Integer mtto_longitud_sumergible;

    @Column(name="mtto_tuberia_diametro")
    private Integer mtto_tuberia_diametro;

    @Column(name="mtto_tuberia_longitud")
    private Integer mtto_tuberia_longitud;

    @Column(name="mtto_observaciones")
    private String mtto_observaciones;

    @Column(name="mtto_panel_solar")
    private String mtto_panel_solar;

    @Column(name="mtto_banco_baterias")
    private String mtto_banco_baterias;

    @Column(name="mtto_medidor_presion")
    private Double mtto_medidor_presion;

    @Column(name="mtto_medidor_flujo")
    private Double mtto_medidor_flujo;

    @Column(name="mtto_medidor_NE")
    private String mtto_medidor_NE;

    @Column(name="mtto_medidor_ND")
    private String mtto_medidor_ND;

    @Column(name="mtto_sistema_tierras")
    private String mtto_sistema_tierras;

    @Column(name="mtto_cable_transformador")
    private String mtto_cable_transformador;

    @Column(name="mtto_cable_bomba")
    private String mtto_cable_bomba;

    @Column(name="mtto_longitud_arrancador")
    private Double mtto_longitud_arrancador;

    @Column(name="mtto_longitud_bomba")
    private Double mtto_longitud_bomba;

    @Column(name="mtto_calibre_arrancador")
    private String mtto_calibre_arrancador;

    @Column(name="mtto_calibre_bomba")
    private String mtto_calibre_bomba;

    @Column(name="mtto_poliducto")
    private String mtto_poliducto;

    @Column(name="mtto_trans_seco")
    private String mtto_trans_seco;

    @Column(name="mtto_electrica_exterior")
    private String mtto_electrica_exterior;

    @Column(name="mtto_electrica_inferior")
    private String mtto_electrica_inferior;

    @Column(name="mtto_sanitaria")
    private String mtto_sanitaria;

    @Column(name="mtto_bomba_marca")
    private String mtto_bomba_marca;

    @Column(name="mtto_bomba_lps")
    private Double mtto_bomba_lps;

    @Column(name="mtto_bomba_rpm")
    private Integer mtto_bomba_rpm;

    @Column(name="mtto_bomba_hp")
    private Double mtto_bomba_hp;

    @Column(name="mtto_tablero_marca")
    private String mtto_tablero_marca;

    @Column(name="mtto_tablero_hp")
    private Double mtto_tablero_hp;

    @Column(name="mtto_tablero_voltaje")
    private Integer mtto_tablero_voltaje;

    @Column(name="mtto_transformador_marca")
    private String mtto_transformador_marca;


    // --- GETTERS Y SETTERS --- //

    public Integer getIdLp() {
        return idLp;
    }

    public void setIdLp(Integer idLp) {
        this.idLp = idLp;
    }

    public Integer getMtto_operador() {
        return mtto_operador;
    }

    public void setMtto_operador(Integer mtto_operador) {
        this.mtto_operador = mtto_operador;
    }

    public String getMtto_clave_pozo() {
        return mtto_clave_pozo;
    }

    public void setMtto_clave_pozo(String mtto_clave_pozo) {
        this.mtto_clave_pozo = mtto_clave_pozo;
    }

    public LocalDateTime getMtto_fecha_captura() {
        return mtto_fecha_captura;
    }

    public void setMtto_fecha_captura(LocalDateTime mtto_fecha_captura) {
        this.mtto_fecha_captura = mtto_fecha_captura;
    }

    public String getMtto_motor_tipo() {
        return mtto_motor_tipo;
    }

    public void setMtto_motor_tipo(String mtto_motor_tipo) {
        this.mtto_motor_tipo = mtto_motor_tipo;
    }

    public Integer getMtto_motor_hp() {
        return mtto_motor_hp;
    }

    public void setMtto_motor_hp(Integer mtto_motor_hp) {
        this.mtto_motor_hp = mtto_motor_hp;
    }

    public Integer getMtto_motor_kw() {
        return mtto_motor_kw;
    }

    public void setMtto_motor_kw(Integer mtto_motor_kw) {
        this.mtto_motor_kw = mtto_motor_kw;
    }

    public Double getMtto_motor_eficiencia() {
        return mtto_motor_eficiencia;
    }

    public void setMtto_motor_eficiencia(Double mtto_motor_eficiencia) {
        this.mtto_motor_eficiencia = mtto_motor_eficiencia;
    }

    public String getMtto_tablero_tipo() {
        return mtto_tablero_tipo;
    }

    public void setMtto_tablero_tipo(String mtto_tablero_tipo) {
        this.mtto_tablero_tipo = mtto_tablero_tipo;
    }

    public Integer getMtto_tablero_capacidad() {
        return mtto_tablero_capacidad;
    }

    public void setMtto_tablero_capacidad(Integer mtto_tablero_capacidad) {
        this.mtto_tablero_capacidad = mtto_tablero_capacidad;
    }

    public String getMtto_transformador_tipo() {
        return mtto_transformador_tipo;
    }

    public void setMtto_transformador_tipo(String mtto_transformador_tipo) {
        this.mtto_transformador_tipo = mtto_transformador_tipo;
    }

    public Integer getMtto_transformador_capacidad() {
        return mtto_transformador_capacidad;
    }

    public void setMtto_transformador_capacidad(Integer mtto_transformador_capacidad) {
        this.mtto_transformador_capacidad = mtto_transformador_capacidad;
    }

    public String getMtto_calibre_sumergible() {
        return mtto_calibre_sumergible;
    }

    public void setMtto_calibre_sumergible(String mtto_calibre_sumergible) {
        this.mtto_calibre_sumergible = mtto_calibre_sumergible;
    }

    public Integer getMtto_longitud_sumergible() {
        return mtto_longitud_sumergible;
    }

    public void setMtto_longitud_sumergible(Integer mtto_longitud_sumergible) {
        this.mtto_longitud_sumergible = mtto_longitud_sumergible;
    }

    public Integer getMtto_tuberia_diametro() {
        return mtto_tuberia_diametro;
    }

    public void setMtto_tuberia_diametro(Integer mtto_tuberia_diametro) {
        this.mtto_tuberia_diametro = mtto_tuberia_diametro;
    }

    public Integer getMtto_tuberia_longitud() {
        return mtto_tuberia_longitud;
    }

    public void setMtto_tuberia_longitud(Integer mtto_tuberia_longitud) {
        this.mtto_tuberia_longitud = mtto_tuberia_longitud;
    }

    public String getMtto_observaciones() {
        return mtto_observaciones;
    }

    public void setMtto_observaciones(String mtto_observaciones) {
        this.mtto_observaciones = mtto_observaciones;
    }

    public String getMtto_panel_solar() {
        return mtto_panel_solar;
    }

    public void setMtto_panel_solar(String mtto_panel_solar) {
        this.mtto_panel_solar = mtto_panel_solar;
    }

    public String getMtto_banco_baterias() {
        return mtto_banco_baterias;
    }

    public void setMtto_banco_baterias(String mtto_banco_baterias) {
        this.mtto_banco_baterias = mtto_banco_baterias;
    }

    public Double getMtto_medidor_presion() {
        return mtto_medidor_presion;
    }

    public void setMtto_medidor_presion(Double mtto_medidor_presion) {
        this.mtto_medidor_presion = mtto_medidor_presion;
    }

    public Double getMtto_medidor_flujo() {
        return mtto_medidor_flujo;
    }

    public void setMtto_medidor_flujo(Double mtto_medidor_flujo) {
        this.mtto_medidor_flujo = mtto_medidor_flujo;
    }

    public String getMtto_medidor_NE() {
        return mtto_medidor_NE;
    }

    public void setMtto_medidor_NE(String mtto_medidor_NE) {
        this.mtto_medidor_NE = mtto_medidor_NE;
    }

    public String getMtto_medidor_ND() {
        return mtto_medidor_ND;
    }

    public void setMtto_medidor_ND(String mtto_medidor_ND) {
        this.mtto_medidor_ND = mtto_medidor_ND;
    }

    public String getMtto_sistema_tierras() {
        return mtto_sistema_tierras;
    }

    public void setMtto_sistema_tierras(String mtto_sistema_tierras) {
        this.mtto_sistema_tierras = mtto_sistema_tierras;
    }

    public String getMtto_cable_transformador() {
        return mtto_cable_transformador;
    }

    public void setMtto_cable_transformador(String mtto_cable_transformador) {
        this.mtto_cable_transformador = mtto_cable_transformador;
    }

    public String getMtto_cable_bomba() {
        return mtto_cable_bomba;
    }

    public void setMtto_cable_bomba(String mtto_cable_bomba) {
        this.mtto_cable_bomba = mtto_cable_bomba;
    }

    public Double getMtto_longitud_arrancador() {
        return mtto_longitud_arrancador;
    }

    public void setMtto_longitud_arrancador(Double mtto_longitud_arrancador) {
        this.mtto_longitud_arrancador = mtto_longitud_arrancador;
    }

    public Double getMtto_longitud_bomba() {
        return mtto_longitud_bomba;
    }

    public void setMtto_longitud_bomba(Double mtto_longitud_bomba) {
        this.mtto_longitud_bomba = mtto_longitud_bomba;
    }

    public String getMtto_calibre_arrancador() {
        return mtto_calibre_arrancador;
    }

    public void setMtto_calibre_arrancador(String mtto_calibre_arrancador) {
        this.mtto_calibre_arrancador = mtto_calibre_arrancador;
    }

    public String getMtto_calibre_bomba() {
        return mtto_calibre_bomba;
    }

    public void setMtto_calibre_bomba(String mtto_calibre_bomba) {
        this.mtto_calibre_bomba = mtto_calibre_bomba;
    }

    public String getMtto_poliducto() {
        return mtto_poliducto;
    }

    public void setMtto_poliducto(String mtto_poliducto) {
        this.mtto_poliducto = mtto_poliducto;
    }

    public String getMtto_trans_seco() {
        return mtto_trans_seco;
    }

    public void setMtto_trans_seco(String mtto_trans_seco) {
        this.mtto_trans_seco = mtto_trans_seco;
    }

    public String getMtto_electrica_exterior() {
        return mtto_electrica_exterior;
    }

    public void setMtto_electrica_exterior(String mtto_electrica_exterior) {
        this.mtto_electrica_exterior = mtto_electrica_exterior;
    }

    public String getMtto_electrica_inferior() {
        return mtto_electrica_inferior;
    }

    public void setMtto_electrica_inferior(String mtto_electrica_inferior) {
        this.mtto_electrica_inferior = mtto_electrica_inferior;
    }

    public String getMtto_sanitaria() {
        return mtto_sanitaria;
    }

    public void setMtto_sanitaria(String mtto_sanitaria) {
        this.mtto_sanitaria = mtto_sanitaria;
    }

    public String getMtto_bomba_marca() {
        return mtto_bomba_marca;
    }

    public void setMtto_bomba_marca(String mtto_bomba_marca) {
        this.mtto_bomba_marca = mtto_bomba_marca;
    }

    public Double getMtto_bomba_lps() {
        return mtto_bomba_lps;
    }

    public void setMtto_bomba_lps(Double mtto_bomba_lps) {
        this.mtto_bomba_lps = mtto_bomba_lps;
    }

    public Integer getMtto_bomba_rpm() {
        return mtto_bomba_rpm;
    }

    public void setMtto_bomba_rpm(Integer mtto_bomba_rpm) {
        this.mtto_bomba_rpm = mtto_bomba_rpm;
    }

    public Double getMtto_bomba_hp() {
        return mtto_bomba_hp;
    }

    public void setMtto_bomba_hp(Double mtto_bomba_hp) {
        this.mtto_bomba_hp = mtto_bomba_hp;
    }

    public String getMtto_tablero_marca() {
        return mtto_tablero_marca;
    }

    public void setMtto_tablero_marca(String mtto_tablero_marca) {
        this.mtto_tablero_marca = mtto_tablero_marca;
    }

    public Double getMtto_tablero_hp() {
        return mtto_tablero_hp;
    }

    public void setMtto_tablero_hp(Double mtto_tablero_hp) {
        this.mtto_tablero_hp = mtto_tablero_hp;
    }

    public Integer getMtto_tablero_voltaje() {
        return mtto_tablero_voltaje;
    }

    public void setMtto_tablero_voltaje(Integer mtto_tablero_voltaje) {
        this.mtto_tablero_voltaje = mtto_tablero_voltaje;
    }

    public String getMtto_transformador_marca() {
        return mtto_transformador_marca;
    }

    public void setMtto_transformador_marca(String mtto_transformador_marca) {
        this.mtto_transformador_marca = mtto_transformador_marca;
    }

}
