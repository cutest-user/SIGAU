package com.sigau.inspeccion.repository;

import com.sigau.inspeccion.Inspeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspeccionRepository
        extends JpaRepository<Inspeccion, Integer> {

    List<Inspeccion> findByEjemplar_Id(Integer ejemplarId);
}
