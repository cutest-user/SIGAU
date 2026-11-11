
package com.sigau.inspeccion.repository;

import com.sigau.inspeccion.EvidenciaInspeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidenciaInspeccionRepository
        extends JpaRepository<EvidenciaInspeccion, Integer> {

    List<EvidenciaInspeccion> findByInspeccionId(Integer inspeccionId);
}
