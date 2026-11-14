package com.sigau.derivacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ActualizarDerivacionRequest {

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado admite hasta 50 caracteres")
    private String estado;

    @Size(max = 100, message = "La referencia admite hasta 100 caracteres")
    private String referenciaExterna;

    public ActualizarDerivacionRequest() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReferenciaExterna() {
        return referenciaExterna;
    }

    public void setReferenciaExterna(String referenciaExterna) {
        this.referenciaExterna = referenciaExterna;
    }
}
