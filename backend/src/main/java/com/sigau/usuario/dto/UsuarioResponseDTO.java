package com.sigau.usuario.dto;

public class UsuarioResponseDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private Integer rolId;
    private Boolean activo;

    public UsuarioResponseDTO(
            Integer id,
            String nombre,
            String apellido,
            String email,
            Integer rolId,
            Boolean activo) {

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rolId = rolId;
        this.activo = activo;
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

    public Boolean getActivo() {
        return activo;
    }
}
