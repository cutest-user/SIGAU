package com.sigau.seguimiento.service;

import com.sigau.certificacion.Certificacion;
import com.sigau.certificacion.repository.CertificacionRepository;
import com.sigau.inspeccion.Inspeccion;
import com.sigau.inspeccion.repository.InspeccionRepository;
import com.sigau.intervencion.Intervencion;
import com.sigau.intervencion.repository.IntervencionRepository;
import com.sigau.relacion.demandaejemplar.DemandaEjemplar;
import com.sigau.relacion.demandaejemplar.repository.DemandaEjemplarRepository;
import com.sigau.seguimiento.dto.SeguimientoHistorialDTO;
import com.sigau.verificacion.Verificacion;
import com.sigau.verificacion.repository.VerificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguimientoService {

    private final DemandaEjemplarRepository demandaEjemplarRepository;
    private final InspeccionRepository inspeccionRepository;
    private final IntervencionRepository intervencionRepository;
    private final VerificacionRepository verificacionRepository;
    private final CertificacionRepository certificacionRepository;

    public SeguimientoService(
            DemandaEjemplarRepository demandaEjemplarRepository,
            InspeccionRepository inspeccionRepository,
            IntervencionRepository intervencionRepository,
            VerificacionRepository verificacionRepository,
            CertificacionRepository certificacionRepository) {

        this.demandaEjemplarRepository = demandaEjemplarRepository;
        this.inspeccionRepository = inspeccionRepository;
        this.intervencionRepository = intervencionRepository;
        this.verificacionRepository = verificacionRepository;
        this.certificacionRepository = certificacionRepository;
    }

    public List<DemandaEjemplar> buscarDemandasPorEjemplar(Integer ejemplarId) {
        return demandaEjemplarRepository.findByEjemplar_Id(ejemplarId);
    }

    public SeguimientoHistorialDTO obtenerHistorial(Integer ejemplarId) {

        List<DemandaEjemplar> demandas =
                demandaEjemplarRepository.findByEjemplar_Id(ejemplarId);

        List<Inspeccion> inspecciones =
                inspeccionRepository.findByEjemplar_Id(ejemplarId);

        List<Intervencion> intervenciones =
                intervencionRepository.findByEjemplar_Id(ejemplarId);

        List<Verificacion> verificaciones =
                verificacionRepository.findByIntervencion_Ejemplar_Id(ejemplarId);

        List<Integer> demandaIds = demandas.stream()
                .map(demandaEjemplar -> demandaEjemplar.getDemanda().getId())
                .distinct()
                .toList();

        List<Certificacion> certificaciones = demandaIds.isEmpty()
                ? List.of()
                : certificacionRepository.findByDemanda_IdIn(demandaIds);

        return new SeguimientoHistorialDTO(
                demandas,
                inspecciones,
                intervenciones,
                verificaciones,
                certificaciones
        );
    }
}
