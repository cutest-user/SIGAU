package com.sigau.intervencion.repository;

import com.sigau.intervencion.Intervencion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntervencionRepository
        extends JpaRepository<Intervencion, Integer> {

    List<Intervencion> findByEjemplar_Id(Integer ejemplarId);

    List<Intervencion> findByDemanda_Id(Integer demandaId);

    List<Intervencion> findByEstadoIn(List<String> estados);
}
