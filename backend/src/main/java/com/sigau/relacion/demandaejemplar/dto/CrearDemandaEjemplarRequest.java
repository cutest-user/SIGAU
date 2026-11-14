package com.sigau.relacion.demandaejemplar.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CrearDemandaEjemplarRequest {

    @NotNull(message = "La demanda es obligatoria")
    private Integer demandaId;
    @NotNull(message = "El ejemplar es obligatorio")
    private Integer ejemplarId;
    @Size(max = 255, message = "La observación admite hasta 255 caracteres")
    private String observacion;

    public CrearDemandaEjemplarRequest() {
    }

    public Integer getDemandaId() {
        return demandaId;
    }

    public void setDemandaId(Integer demandaId) {
        this.demandaId = demandaId;
    }

    public Integer getEjemplarId() {
        return ejemplarId;
    }

    public void setEjemplarId(Integer ejemplarId) {
        this.ejemplarId = ejemplarId;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}

