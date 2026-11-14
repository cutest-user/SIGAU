
package com.sigau.usuario.dto;

public class LoginResponseDTO {

    private String token;
    private String tipo;
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private Integer rolId;

    public LoginResponseDTO(
            String token,
            String tipo,
            Integer id,
            String nombre,
            String apellido,
            String email,
            Integer rolId) {

        this.token = token;
        this.tipo = tipo;
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rolId = rolId;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public Integer getRolId() {
        return rolId;
    }
}
