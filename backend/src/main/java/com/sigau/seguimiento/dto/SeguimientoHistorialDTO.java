package com.sigau.seguimiento.dto;

import com.sigau.relacion.demandaejemplar.DemandaEjemplar;
import com.sigau.inspeccion.Inspeccion;
import com.sigau.intervencion.Intervencion;
import com.sigau.verificacion.Verificacion;
import com.sigau.certificacion.Certificacion;

import java.util.List;

public class SeguimientoHistorialDTO {

    private List<DemandaEjemplar> demandas;
    private List<Inspeccion> inspecciones;
    private List<Intervencion> intervenciones;
    private List<Verificacion> verificaciones;
    private List<Certificacion> certificaciones;

    public SeguimientoHistorialDTO() {
    }

    public SeguimientoHistorialDTO(
            List<DemandaEjemplar> demandas,
            List<Inspeccion> inspecciones,
            List<Intervencion> intervenciones,
            List<Verificacion> verificaciones,
            List<Certificacion> certificaciones) {

        this.demandas = demandas;
        this.inspecciones = inspecciones;
        this.intervenciones = intervenciones;
        this.verificaciones = verificaciones;
        this.certificaciones = certificaciones;
    }

    public List<DemandaEjemplar> getDemandas() {
        return demandas;
    }

    public void setDemandas(List<DemandaEjemplar> demandas) {
        this.demandas = demandas;
    }

    public List<Inspeccion> getInspecciones() {
        return inspecciones;
    }

    public void setInspecciones(List<Inspeccion> inspecciones) {
        this.inspecciones = inspecciones;
    }

    public List<Intervencion> getIntervenciones() {
        return intervenciones;
    }

    public void setIntervenciones(List<Intervencion> intervenciones) {
        this.intervenciones = intervenciones;
    }

    public List<Verificacion> getVerificaciones() {
        return verificaciones;
    }

    public void setVerificaciones(List<Verificacion> verificaciones) {
        this.verificaciones = verificaciones;
    }

    public List<Certificacion> getCertificaciones() {
        return certificaciones;
    }

    public void setCertificaciones(List<Certificacion> certificaciones) {
        this.certificaciones = certificaciones;
    }
}
