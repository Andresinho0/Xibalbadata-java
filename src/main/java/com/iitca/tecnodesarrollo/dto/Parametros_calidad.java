package com.iitca.tecnodesarrollo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "parametros_calidad")
public class Parametros_calidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	// Llave foránea hacia lista_pozos.Id_lp
	@Column(name = "pozo_id")
	private Integer pozoId;

	@Column(name = "temperatura_c")
	private Double temperaturaC;

	@Column(name = "ph")
	private Double ph;

	@Column(name = "ce_us_cm")
	private Double ceUsCm;

	@Column(name = "sdt_ppm")
	private Double sdtPpm;

	@Column(name = "eh_mv")
	private Double ehMv;

	@Column(name = "od_porcentaje")
	private Double odPorcentaje;

	@Column(name = "od_mg_l")
	private Double odMgL;

	@Column(name = "cloruros_mg_l")
	private Double clorurosMgL;

	// String porque el Excel trae valores como "0.04*" y "N.D"
	@Column(name = "nitratos_mg_l")
	private String nitratosMgL;

	@Column(name = "sulfatos_mg_l")
	private Double sulfatosMgL;

	@Column(name = "alcalinidad_mg_caco3_l")
	private Double alcalinidadMgCaco3L;

	@Column(name = "bicarbonatos_mg_l")
	private Double bicarbonatosMgL;

	@Column(name = "coliformes_totales")
	private String coliformesTotales;

	@Column(name = "e_coli")
	private String eColi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPozoId() {
        return pozoId;
    }

    public void setPozoId(Integer pozoId) {
        this.pozoId = pozoId;
    }

    public Double getTemperaturaC() {
        return temperaturaC;
    }

    public void setTemperaturaC(Double temperaturaC) {
        this.temperaturaC = temperaturaC;
    }

    public Double getPh() {
        return ph;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public Double getCeUsCm() {
        return ceUsCm;
    }

    public void setCeUsCm(Double ceUsCm) {
        this.ceUsCm = ceUsCm;
    }

    public Double getSdtPpm() {
        return sdtPpm;
    }

    public void setSdtPpm(Double sdtPpm) {
        this.sdtPpm = sdtPpm;
    }

    public Double getEhMv() {
        return ehMv;
    }

    public void setEhMv(Double ehMv) {
        this.ehMv = ehMv;
    }

    public Double getOdPorcentaje() {
        return odPorcentaje;
    }

    public void setOdPorcentaje(Double odPorcentaje) {
        this.odPorcentaje = odPorcentaje;
    }

    public Double getOdMgL() {
        return odMgL;
    }

    public void setOdMgL(Double odMgL) {
        this.odMgL = odMgL;
    }

    public Double getClorurosMgL() {
        return clorurosMgL;
    }

    public void setClorurosMgL(Double clorurosMgL) {
        this.clorurosMgL = clorurosMgL;
    }

    public String getNitratosMgL() {
        return nitratosMgL;
    }

    public void setNitratosMgL(String nitratosMgL) {
        this.nitratosMgL = nitratosMgL;
    }

    public Double getSulfatosMgL() {
        return sulfatosMgL;
    }

    public void setSulfatosMgL(Double sulfatosMgL) {
        this.sulfatosMgL = sulfatosMgL;
    }

    public Double getAlcalinidadMgCaco3L() {
        return alcalinidadMgCaco3L;
    }

    public void setAlcalinidadMgCaco3L(Double alcalinidadMgCaco3L) {
        this.alcalinidadMgCaco3L = alcalinidadMgCaco3L;
    }

    public Double getBicarbonatosMgL() {
        return bicarbonatosMgL;
    }

    public void setBicarbonatosMgL(Double bicarbonatosMgL) {
        this.bicarbonatosMgL = bicarbonatosMgL;
    }

    public String getColiformesTotales() {
        return coliformesTotales;
    }

    public void setColiformesTotales(String coliformesTotales) {
        this.coliformesTotales = coliformesTotales;
    }

    public String geteColi() {
        return eColi;
    }

    public void seteColi(String eColi) {
        this.eColi = eColi;
    }
}