
package com.sigau.relacion.demandaejemplar.repository;

import com.sigau.relacion.demandaejemplar.DemandaEjemplar;
import com.sigau.relacion.demandaejemplar.DemandaEjemplarId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandaEjemplarRepository
        extends JpaRepository<DemandaEjemplar, DemandaEjemplarId> {
}

