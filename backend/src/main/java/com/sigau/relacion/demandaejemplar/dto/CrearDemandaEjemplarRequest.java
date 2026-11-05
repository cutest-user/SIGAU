
package com.sigau.relacion.demandaejemplar.dto;

public class CrearDemandaEjemplarRequest {

    private Integer demandaId;
    private Integer ejemplarId;
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

