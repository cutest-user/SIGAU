package com.sigau.demanda.repository;

import com.sigau.demanda.Demanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandaRepository extends JpaRepository<Demanda, Integer> {

    List<Demanda> findByUbicacion_IdAndEstadoIn(
            Integer ubicacionId, List<String> estados);
}
