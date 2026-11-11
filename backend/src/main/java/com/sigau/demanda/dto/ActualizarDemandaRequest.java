
package com.sigau.demanda.dto;

public class ActualizarDemandaRequest {

    private Integer origenId;
    private Integer ubicacionId;
    private String motivo;
    private String descripcion;
    private String prioridad;
    private String observaciones;

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

