package com.sigau.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UbicacionRequest {

    @NotBlank(message = "La calle es obligatoria")
    @Size(max = 150, message = "La calle admite hasta 150 caracteres")
    private String calle;

    @Size(max = 20, message = "La altura admite hasta 20 caracteres")
    private String numero;

    @Size(max = 100, message = "El barrio admite hasta 100 caracteres")
    private String barrio;

    @Size(max = 50, message = "La comuna admite hasta 50 caracteres")
    private String comuna;

    @Size(max = 255, message = "La referencia admite hasta 255 caracteres")
    private String referencia;

    public UbicacionRequest() {
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
