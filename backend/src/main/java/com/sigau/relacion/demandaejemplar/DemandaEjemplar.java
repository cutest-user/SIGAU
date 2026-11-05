
package com.sigau.relacion.demandaejemplar;

import com.sigau.demanda.Demanda;
import com.sigau.ejemplar.Ejemplar;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "demanda_ejemplar")
public class DemandaEjemplar {

    @EmbeddedId
    private DemandaEjemplarId id;

    @MapsId("demandaId")
    @ManyToOne
    @JoinColumn(name = "demanda_id")
    private Demanda demanda;

    @MapsId("ejemplarId")
    @ManyToOne
    @JoinColumn(name = "ejemplar_id")
    private Ejemplar ejemplar;

    @Column(name = "fecha_asociacion")
    private LocalDateTime fechaAsociacion;

    @Column(length = 255)
    private String observacion;

    public DemandaEjemplar() {
    }

    public DemandaEjemplarId getId() {
        return id;
    }

    public void setId(DemandaEjemplarId id) {
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

    public LocalDateTime getFechaAsociacion() {
        return fechaAsociacion;
    }

    public void setFechaAsociacion(LocalDateTime fechaAsociacion) {
        this.fechaAsociacion = fechaAsociacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}

