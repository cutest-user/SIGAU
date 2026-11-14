package com.sigau.historial.repository;

import com.sigau.historial.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface HistorialRepository extends JpaRepository<Historial, Integer> {

    List<Historial> findByEntidadAndEntidadIdOrderByIdAsc(
            String entidad, Integer entidadId);

    List<Historial> findByEntidadAndEntidadIdIn(
            String entidad, Collection<Integer> entidadIds);
}
