package com.sigau.demanda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CrearDemandaRequest {

    @NotNull(message = "El origen es obligatorio")
    private Integer origenId;
    private Integer ubicacionId;
    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 255, message = "El motivo admite hasta 255 caracteres")
    private String motivo;
    private String descripcion;
    @Pattern(regexp = "BAJA|MEDIA|ALTA", message = "La prioridad debe ser BAJA, MEDIA o ALTA")
    private String prioridad;
    private String observaciones;

    public CrearDemandaRequest() {
    }

    public Integer getOrigenId() {
        return origenId;
    }

    public void setOrigenId(Integer origenId) {
        this.origenId = origenId;
    }

    public Integer getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(Integer ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
