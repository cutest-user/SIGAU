
package com.sigau.intervencion;

import com.sigau.demanda.Demanda;
import com.sigau.ejemplar.Ejemplar;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "intervencion")
public class Intervencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "demanda_id")
    private Demanda demanda;

    @ManyToOne
    @JoinColumn(name = "ejemplar_id")
    private Ejemplar ejemplar;

    @ManyToOne
    @JoinColumn(name = "tipo_intervencion_id")
    private TipoIntervencion tipoIntervencion;

    @Column(length = 50)
    private String estado;

    @Column(name = "fecha_planificada")
    private LocalDate fechaPlanificada;

    @Column(name = "fecha_ejecucion")
    private LocalDate fechaEjecucion;

    @ManyToOne
    @JoinColumn(name = "intervencion_previa_id")
    private Intervencion intervencionPrevia;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    public Intervencion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Demanda getDemanda() {
        return demanda;
    }

    public void setDemanda(Demanda demanda) {
        this.demanda = demanda;
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public TipoIntervencion getTipoIntervencion() {
        return tipoIntervencion;
    }

    public void setTipoIntervencion(TipoIntervencion tipoIntervencion) {
        this.tipoIntervencion = tipoIntervencion;
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

    public Intervencion getIntervencionPrevia() {
        return intervencionPrevia;
    }

    public void setIntervencionPrevia(Intervencion intervencionPrevia) {
        this.intervencionPrevia = intervencionPrevia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
