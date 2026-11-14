package com.sigau.inspeccion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CrearEvidenciaInspeccionRequest {

    @NotNull(message = "La inspección es obligatoria")
    private Integer inspeccionId;
    @NotBlank(message = "La referencia al archivo es obligatoria")
    @Size(max = 500, message = "La referencia admite hasta 500 caracteres")
    private String url;
    @Size(max = 255, message = "La descripción admite hasta 255 caracteres")
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
