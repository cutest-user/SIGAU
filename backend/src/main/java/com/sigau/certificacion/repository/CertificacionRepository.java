package com.sigau.certificacion.repository;

import com.sigau.certificacion.Certificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificacionRepository
        extends JpaRepository<Certificacion, String> {

    List<Certificacion> findByDemanda_IdIn(List<Integer> demandaIds);
}

