package com.sigau.seguimiento.service;

import com.sigau.certificacion.Certificacion;
import com.sigau.certificacion.repository.CertificacionRepository;
import com.sigau.common.Estados;
import com.sigau.common.exception.RecursoNoEncontradoException;
import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.ejemplar.Ejemplar;
import com.sigau.ejemplar.repository.EjemplarRepository;
import com.sigau.historial.Historial;
import com.sigau.historial.repository.HistorialRepository;
import com.sigau.historial.service.HistorialService;
import com.sigau.inspeccion.Inspeccion;
import com.sigau.inspeccion.repository.InspeccionRepository;
import com.sigau.intervencion.Intervencion;
import com.sigau.intervencion.repository.IntervencionRepository;
import com.sigau.relacion.demandaejemplar.DemandaEjemplar;
import com.sigau.relacion.demandaejemplar.repository.DemandaEjemplarRepository;
import com.sigau.seguimiento.dto.SeguimientoDemandaDTO;
import com.sigau.seguimiento.dto.SeguimientoHistorialDTO;
import com.sigau.verificacion.Verificacion;
import com.sigau.verificacion.repository.VerificacionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SeguimientoService {

    private final DemandaEjemplarRepository demandaEjemplarRepository;
    private final InspeccionRepository inspeccionRepository;
    private final IntervencionRepository intervencionRepository;
    private final VerificacionRepository verificacionRepository;
    private final CertificacionRepository certificacionRepository;
    private final DemandaRepository demandaRepository;
    private final EjemplarRepository ejemplarRepository;
    private final HistorialRepository historialRepository;

    public SeguimientoService(
            DemandaEjemplarRepository demandaEjemplarRepository,
            InspeccionRepository inspeccionRepository,
            IntervencionRepository intervencionRepository,
            VerificacionRepository verificacionRepository,
            CertificacionRepository certificacionRepository,
            DemandaRepository demandaRepository,
            EjemplarRepository ejemplarRepository,
            HistorialRepository historialRepository) {

        this.demandaEjemplarRepository = demandaEjemplarRepository;
        this.inspeccionRepository = inspeccionRepository;
        this.intervencionRepository = intervencionRepository;
        this.verificacionRepository = verificacionRepository;
        this.certificacionRepository = certificacionRepository;
        this.demandaRepository = demandaRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.historialRepository = historialRepository;
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

        List<Historial> cambios = new ArrayList<>(
                historialRepository.findByEntidadAndEntidadIdOrderByIdAsc(
                        HistorialService.EJEMPLAR, ejemplarId));

        if (!intervenciones.isEmpty()) {
            cambios.addAll(historialRepository.findByEntidadAndEntidadIdIn(
                    HistorialService.INTERVENCION,
                    intervenciones.stream().map(Intervencion::getId).toList()));
        }

        cambios.sort(Comparator.comparing(Historial::getId));

        return new SeguimientoHistorialDTO(
                demandas,
                inspecciones,
                intervenciones,
                verificaciones,
                certificaciones,
                cambios
        );
    }

    public SeguimientoDemandaDTO obtenerDemanda(Integer demandaId) {

        Demanda demanda = demandaRepository.findById(demandaId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Demanda no encontrada"));

        List<Intervencion> intervenciones =
                intervencionRepository.findByDemanda_Id(demandaId);

        List<Historial> cambios = new ArrayList<>(
                historialRepository.findByEntidadAndEntidadIdOrderByIdAsc(
                        HistorialService.DEMANDA, demandaId));

        if (!intervenciones.isEmpty()) {
            cambios.addAll(historialRepository.findByEntidadAndEntidadIdIn(
                    HistorialService.INTERVENCION,
                    intervenciones.stream().map(Intervencion::getId).toList()));
        }

        cambios.sort(Comparator.comparing(Historial::getId));

        Certificacion certificacion = certificacionRepository
                .findByDemanda_IdIn(List.of(demandaId))
                .stream()
                .findFirst()
                .orElse(null);

        return new SeguimientoDemandaDTO(
                demanda,
                demandaEjemplarRepository.findByDemanda_Id(demandaId),
                intervenciones,
                verificacionRepository.findByIntervencion_Demanda_Id(demandaId),
                certificacion,
                cambios
        );
    }

    public List<Intervencion> obtenerIntervencionesPendientes() {

        List<String> estadosPendientes = List.of(
                Estados.INTERVENCION_PENDIENTE,
                Estados.INTERVENCION_PLANIFICADA,
                Estados.INTERVENCION_EN_EJECUCION
        );

        return intervencionRepository.findByEstadoIn(estadosPendientes);
    }

    public List<Demanda> obtenerDemandasConEstado() {
        return demandaRepository.findAll();
    }

    public List<Ejemplar> obtenerEjemplaresConEstado() {
        return ejemplarRepository.findAll();
    }
}
