package com.sigau.inspeccion.repository;

import com.sigau.inspeccion.Inspeccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InspeccionRepository
        extends JpaRepository<Inspeccion, Integer> {
}
