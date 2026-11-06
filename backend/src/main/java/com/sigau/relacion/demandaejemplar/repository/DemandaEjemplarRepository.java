package com.sigau.relacion.demandaejemplar.repository;

import com.sigau.relacion.demandaejemplar.DemandaEjemplar;
import com.sigau.relacion.demandaejemplar.DemandaEjemplarId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandaEjemplarRepository
        extends JpaRepository<DemandaEjemplar, DemandaEjemplarId> {

    List<DemandaEjemplar> findByEjemplar_Id(Integer ejemplarId);
}

