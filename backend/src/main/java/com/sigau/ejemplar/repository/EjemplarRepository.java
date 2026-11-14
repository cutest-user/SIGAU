package com.sigau.ejemplar.repository;

import com.sigau.ejemplar.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Integer> {

    List<Ejemplar> findByUbicacion_Id(Integer ubicacionId);
}
