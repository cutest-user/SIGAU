
package com.sigau.reporte.dto;

import java.util.Map;

public class ReporteResponseDTO {

    private Long totalDemandas;
    private Map<String, Long> demandasPorEstado;
    private Map<String, Long> demandasPorOrigen;

    private Long totalIntervenciones;
    private Map<String, Long> intervencionesPorTipo;
    private Map<String, Long> intervencionesPorEstado;

    private Long totalInspecciones;

    private Long totalEjemplares;
    private Map<String, Long> ejemplaresPorEstado;

    public ReporteResponseDTO() {
    }

    public Long getTotalDemandas() {
        return totalDemandas;
    }

    public void setTotalDemandas(Long totalDemandas) {
        this.totalDemandas = totalDemandas;
    }

    public Map<String, Long> getDemandasPorEstado() {
        return demandasPorEstado;
    }

    public void setDemandasPorEstado(Map<String, Long> demandasPorEstado) {
        this.demandasPorEstado = demandasPorEstado;
    }

    public Map<String, Long> getDemandasPorOrigen() {
        return demandasPorOrigen;
    }

    public void setDemandasPorOrigen(Map<String, Long> demandasPorOrigen) {
        this.demandasPorOrigen = demandasPorOrigen;
    }

    public Long getTotalIntervenciones() {
        return totalIntervenciones;
    }

    public void setTotalIntervenciones(Long totalIntervenciones) {
        this.totalIntervenciones = totalIntervenciones;
    }

    public Map<String, Long> getIntervencionesPorTipo() {
        return intervencionesPorTipo;
    }

    public void setIntervencionesPorTipo(Map<String, Long> intervencionesPorTipo) {
        this.intervencionesPorTipo = intervencionesPorTipo;
    }

    public Map<String, Long> getIntervencionesPorEstado() {
        return intervencionesPorEstado;
    }

    public void setIntervencionesPorEstado(Map<String, Long> intervencionesPorEstado) {
        this.intervencionesPorEstado = intervencionesPorEstado;
    }

    public Long getTotalInspecciones() {
        return totalInspecciones;
    }

    public void setTotalInspecciones(Long totalInspecciones) {
        this.totalInspecciones = totalInspecciones;
    }

    public Long getTotalEjemplares() {
        return totalEjemplares;
    }

    public void setTotalEjemplares(Long totalEjemplares) {
        this.totalEjemplares = totalEjemplares;
    }

    public Map<String, Long> getEjemplaresPorEstado() {
        return ejemplaresPorEstado;
    }

    public void setEjemplaresPorEstado(Map<String, Long> ejemplaresPorEstado) {
        this.ejemplaresPorEstado = ejemplaresPorEstado;
    }
}

