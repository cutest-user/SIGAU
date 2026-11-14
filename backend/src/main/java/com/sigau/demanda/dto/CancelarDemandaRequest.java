package com.sigau.demanda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CancelarDemandaRequest {

    @NotBlank(message = "Hay que indicar el motivo de la cancelación")
    @Size(max = 500, message = "El motivo admite hasta 500 caracteres")
    private String observacion;

    public CancelarDemandaRequest() {
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
