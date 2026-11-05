package com.sigau.intervencion.repository;

import com.sigau.intervencion.TipoIntervencion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoIntervencionRepository
        extends JpaRepository<TipoIntervencion, Integer> {
}
