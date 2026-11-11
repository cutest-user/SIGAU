
package com.sigau.inspeccion.dto;

public class CrearEvidenciaInspeccionRequest {

    private Integer inspeccionId;
    private String url;
    private String descripcion;

    public CrearEvidenciaInspeccionRequest() {
    }

    public Integer getInspeccionId() {
        return inspeccionId;
    }

    public void setInspeccionId(Integer inspeccionId) {
        this.inspeccionId = inspeccionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
