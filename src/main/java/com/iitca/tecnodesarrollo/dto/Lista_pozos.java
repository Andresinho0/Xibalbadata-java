package com.iitca.tecnodesarrollo.dto;
import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Lob;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Entity
@Table(name = "lista_pozos")
public class Lista_pozos {

	@Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name="Id_lp")
	private int id_lp;

	@Column(name="l_poz_Clave")
	private String l_poz_clave;

	@Column(name="lp_Nombre")
	private String lp_Nombre;

	@Column(name="lp_ramal")
	private String lp_ramal;

    @Column(name = "lp_Ubicación")
    private String lp_ubicacion;


	@Column(name="lp_Profundidad_perforación")
	private Integer lp_Profundidad_perforación;

    @Column(name="lp_Diámetro_perforación")
	private Integer lp_Diámetro_perforación;

	@Column(name="lp_Diámetro_ADEME")
	private Integer lp_Diámetro_ADEME;

    @Column(name = "lp_subestacion")
    private String lp_subestacion;

    @Column(name = "lp_circuito_electrico")
    private String lp_circuito_electrico;

    @Column(name = "lp_aporta_acueducto")
    private String lp_aporta_acueducto;

    @Column(name = "lp_aporta_comunidad")
    private String lp_aporta_comunidad;

    @Column(name = "lp_aporta_riego")
    private String lp_aporta_riego;

    @Column(name = "lp_pa_cdmx")
    private String lp_pa_cdmx;

    @Column(name = "lp_telemetria")
    private String lp_telemetria;

    @Column(name = "lp_utr")
    private String lp_utr;

    @Column (name = "lp_altura")
    private String lp_altura;

    @Column (name = "lp_estado")
    private String lp_estado;

    @Column (name = "lp_municipio")
    private String lp_municipio;

    @Column (name = "lp_dependencia_informante")
    private String lp_dependencia_informante;

    @Column (name = "lp_columna_succion", precision = 10, scale = 2)
    private Double lp_columna_succion;






    public int getId_lp() {
        return id_lp;
    }
    public void setId_lp(int id_lp) {
        this.id_lp = id_lp;
    }

    public String getL_poz_clave() {
        return l_poz_clave;
    }
    public void setL_poz_clave(String l_poz_clave) {
        this.l_poz_clave = l_poz_clave;
    }

    public String getLp_Nombre() {
        return lp_Nombre;
    }
    public void setLp_Nombre(String lp_Nombre) {
        this.lp_Nombre = lp_Nombre;
    }

    public String getLp_ramal() {
        return lp_ramal;
    }
    public void setLp_ramal(String lp_ramal) {
        this.lp_ramal = lp_ramal;
    }

    public Integer getLp_Profundidad_perforación() {
        return lp_Profundidad_perforación;
    }
    public void setLp_Profundidad_perforación(Integer lp_Profundidad_perforación) {
        this.lp_Profundidad_perforación = lp_Profundidad_perforación;
    }

    public Integer getLp_Diámetro_perforación() {
        return lp_Diámetro_perforación;
    }
    public void setLp_Diámetro_perforación(Integer lp_Diámetro_perforación) {
        this.lp_Diámetro_perforación = lp_Diámetro_perforación;
    }

    public Integer getLp_Diámetro_ADEME() {
        return lp_Diámetro_ADEME;
    }
    public void setLp_Diámetro_ADEME(Integer lp_Diámetro_ADEME) {
        this.lp_Diámetro_ADEME = lp_Diámetro_ADEME;
    }

    public String getLp_subestacion() {
        return lp_subestacion;
    }
    public void setLp_subestacion(String lp_subestacion) {
        this.lp_subestacion = lp_subestacion;
    }

    public String getLp_circuito_electrico() {
        return lp_circuito_electrico;
    }
    public void setLp_circuito_electrico(String lp_circuito_electrico) {
        this.lp_circuito_electrico = lp_circuito_electrico;
    }

    public String getLp_aporta_acueducto() {
        return lp_aporta_acueducto;
    }
    public void setLp_aporta_acueducto(String lp_aporta_acueducto) {
        this.lp_aporta_acueducto = lp_aporta_acueducto;
    }

    public String getLp_aporta_comunidad() {
        return lp_aporta_comunidad;
    }
    public void setLp_aporta_comunidad(String lp_aporta_comunidad) {
        this.lp_aporta_comunidad = lp_aporta_comunidad;
    }

    public String getLp_aporta_riego() {
        return lp_aporta_riego;
    }
    public void setLp_aporta_riego(String lp_aporta_riego) {
        this.lp_aporta_riego = lp_aporta_riego;
    }

    public String getLp_pa_cdmx() {
        return lp_pa_cdmx;
    }
    public void setLp_pa_cdmx(String lp_pa_cdmx) {
        this.lp_pa_cdmx = lp_pa_cdmx;
    }

    public String getLp_telemetria() {
        return lp_telemetria;
    }
    public void setLp_telemetria(String lp_telemetria) {
        this.lp_telemetria = lp_telemetria;
    }

    public String getLp_utr() {
        return lp_utr;
    }
    public void setLp_utr(String lp_utr) {
        this.lp_utr = lp_utr;
    }

    public String getLp_ubicacion() {
        return lp_ubicacion;
    }

    public void setLp_ubicacion(String lp_ubicacion) {
        this.lp_ubicacion = lp_ubicacion;
    }

    public String getLp_altura() {
        return lp_altura;
    }

    public void setLp_altura(String lp_altura) {
        this.lp_altura = lp_altura;
    }

    public String getLp_estado() {
        return lp_estado;
    }

    public void setLp_estado(String lp_estado) {
        this.lp_estado = lp_estado;
    }

    public String getLp_municipio() {
        return lp_municipio;
    }

    public void setLp_municipio(String lp_municipio) {
        this.lp_municipio = lp_municipio;
    }

    public String getLp_dependencia_informante() {
        return lp_dependencia_informante;
    }

    public void setLp_dependencia_informante(String lp_dependencia_informante) {
        this.lp_dependencia_informante = lp_dependencia_informante;
    }

    public Double getLp_columna_succion() {
        return lp_columna_succion;
    }

    public void setLp_columna_succion(Double lp_columna_succion) {
        this.lp_columna_succion = lp_columna_succion;
    }


    
    }
	