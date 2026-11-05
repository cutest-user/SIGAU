
package com.sigau.relacion.demandaejemplar;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DemandaEjemplarId implements Serializable {

    @Column(name = "demanda_id")
    private Integer demandaId;

    @Column(name = "ejemplar_id")
    private Integer ejemplarId;

    public DemandaEjemplarId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemandaEjemplarId that)) return false;
        return Objects.equals(demandaId, that.demandaId)
                && Objects.equals(ejemplarId, that.ejemplarId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(demandaId, ejemplarId);
    }
}

