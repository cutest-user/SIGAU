package com.sigau.verificacion.repository;

import com.sigau.verificacion.Verificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificacionRepository
        extends JpaRepository<Verificacion, Integer> {

    List<Verificacion> findByIntervencion_Ejemplar_Id(Integer ejemplarId);
}

