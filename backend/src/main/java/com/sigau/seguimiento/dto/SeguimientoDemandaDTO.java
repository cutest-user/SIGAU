package com.sigau.seguimiento.dto;

import com.sigau.certificacion.Certificacion;
import com.sigau.demanda.Demanda;
import com.sigau.historial.Historial;
import com.sigau.intervencion.Intervencion;
import com.sigau.relacion.demandaejemplar.DemandaEjemplar;
import com.sigau.verificacion.Verificacion;

import java.util.List;

public class SeguimientoDemandaDTO {

    private Demanda demanda;
    private List<DemandaEjemplar> ejemplares;
    private List<Intervencion> intervenciones;
    private List<Verificacion> verificaciones;
    private Certificacion certificacion;
    private List<Historial> cambios;

    public SeguimientoDemandaDTO() {
    }

    public SeguimientoDemandaDTO(
            Demanda demanda,
            List<DemandaEjemplar> ejemplares,
            List<Intervencion> intervenciones,
            List<Verificacion> verificaciones,
            Certificacion certificacion,
            List<Historial> cambios) {

        this.demanda = demanda;
        this.ejemplares = ejemplares;
        this.intervenciones = intervenciones;
        this.verificaciones = verificaciones;
        this.certificacion = certificacion;
        this.cambios = cambios;
    }

    public Demanda getDemanda() {
        return demanda;
    }

    public List<DemandaEjemplar> getEjemplares() {
        return ejemplares;
    }

    public List<Intervencion> getIntervenciones() {
        return intervenciones;
    }

    public List<Verificacion> getVerificaciones() {
        return verificaciones;
    }

    public Certificacion getCertificacion() {
        return certificacion;
    }

    public List<Historial> getCambios() {
        return cambios;
    }
}
