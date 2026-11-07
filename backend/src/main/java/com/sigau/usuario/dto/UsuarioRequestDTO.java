package com.sigau.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    private String apellido;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El formato del email no es válido.")
    private String email;

    private String password;

    @NotNull(message = "El rol es obligatorio.")
    @Positive(message = "El ID del rol debe ser positivo.")
    private Integer rolId;

    private Boolean activo;

    public UsuarioRequestDTO() {
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

    public String getPassword() {
        return password;
    }

    public Integer getRolId() {
        return rolId;
    }

    public Boolean getActivo() {
        return activo;
    }
}
