package com.iitca.tecnodesarrollo.dto;

/**
 * Valores permitidos para la columna "estado" de operacion y mantenimiento.
 * Deben coincidir con los CHECK constraints de la base de datos.
 */
public final class EstadoRegistro {

    public static final String PENDIENTE = "pendiente";
    public static final String COMPLETADA = "completada";

    private EstadoRegistro() {
    }
}
