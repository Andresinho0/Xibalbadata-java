package com.iitca.tecnodesarrollo.dto;

public class CambiarContrasenaRequest {
    private String token;
    private String nuevaContrasena;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getNuevaContrasena() { return nuevaContrasena; }
    public void setNuevaContrasena(String nuevaContrasena) { this.nuevaContrasena = nuevaContrasena; }
}
