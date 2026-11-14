package com.sigau.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 100, message = "El nombre admite hasta 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(max = 150, message = "El apellido admite hasta 150 caracteres.")
    private String apellido;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El formato del email no es válido.")
    @Size(max = 150, message = "El email admite hasta 150 caracteres.")
    private String email;

    @Size(min = 8, max = 72,
            message = "La contraseña debe tener entre 8 y 72 caracteres.")
    private String password;

    @NotNull(message = "El rol es obligatorio.")
    @Min(value = 1, message = "El rol indicado no existe.")
    @Max(value = 2, message = "El rol indicado no existe.")
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
