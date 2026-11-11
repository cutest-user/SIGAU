
package com.sigau.inspeccion.service;

import com.sigau.inspeccion.EvidenciaInspeccion;
import com.sigau.inspeccion.Inspeccion;
import com.sigau.inspeccion.dto.CrearEvidenciaInspeccionRequest;
import com.sigau.inspeccion.repository.EvidenciaInspeccionRepository;
import com.sigau.inspeccion.repository.InspeccionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EvidenciaInspeccionService {

    private final EvidenciaInspeccionRepository evidenciaRepository;
    private final InspeccionRepository inspeccionRepository;

    public EvidenciaInspeccionService(
            EvidenciaInspeccionRepository evidenciaRepository,
            InspeccionRepository inspeccionRepository) {

        this.evidenciaRepository = evidenciaRepository;
        this.inspeccionRepository = inspeccionRepository;
    }

    public EvidenciaInspeccion guardar(CrearEvidenciaInspeccionRequest request) {

        Inspeccion inspeccion = inspeccionRepository.findById(request.getInspeccionId())
                .orElseThrow(() -> new RuntimeException("Inspección no encontrada"));

        EvidenciaInspeccion evidencia = new EvidenciaInspeccion();

        evidencia.setInspeccion(inspeccion);
        evidencia.setUrl(request.getUrl());
        evidencia.setDescripcion(request.getDescripcion());
        evidencia.setFecha(LocalDate.now());

        return evidenciaRepository.save(evidencia);
    }

    public List<EvidenciaInspeccion> listarPorInspeccion(Integer inspeccionId) {

        return evidenciaRepository.findByInspeccionId(inspeccionId);
    }
}
