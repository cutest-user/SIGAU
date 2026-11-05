package com.sigau.intervencion.repository;

import com.sigau.intervencion.Intervencion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntervencionRepository
        extends JpaRepository<Intervencion, Integer> {
}
