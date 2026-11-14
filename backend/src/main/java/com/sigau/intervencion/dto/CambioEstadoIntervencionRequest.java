package com.sigau.intervencion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CambioEstadoIntervencionRequest {

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    private LocalDate fechaPlanificada;

    private LocalDate fechaEjecucion;

    @Size(max = 500, message = "La observación admite hasta 500 caracteres")
    private String observacion;

    public CambioEstadoIntervencionRequest() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPlanificada() {
        return fechaPlanificada;
    }

    public void setFechaPlanificada(LocalDate fechaPlanificada) {
        this.fechaPlanificada = fechaPlanificada;
    }

    public LocalDate getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDate fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
