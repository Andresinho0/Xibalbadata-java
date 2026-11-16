package com.iitca.tecnodesarrollo.dto;

/**
 * Cuerpo de la petición para marcar una operación o mantenimiento como completado.
 * JSON esperado: { "completada_por": 5, "notas_cierre": "..." }
 */
public class CierreRequest {

    private Integer completada_por;
    private String notas_cierre;

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
}
