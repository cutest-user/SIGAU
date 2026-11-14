package com.sigau.intervencion.service;

import com.sigau.common.Estados;
import com.sigau.common.exception.RecursoNoEncontradoException;
import com.sigau.common.exception.ReglaNegocioException;
import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.ejemplar.Ejemplar;
import com.sigau.ejemplar.repository.EjemplarRepository;
import com.sigau.historial.service.HistorialService;
import com.sigau.intervencion.Intervencion;
import com.sigau.intervencion.TipoIntervencion;
import com.sigau.intervencion.dto.CambioEstadoIntervencionRequest;
import com.sigau.intervencion.repository.IntervencionRepository;
import com.sigau.intervencion.repository.TipoIntervencionRepository;
import com.sigau.relacion.demandaejemplar.DemandaEjemplarId;
import com.sigau.relacion.demandaejemplar.repository.DemandaEjemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class IntervencionService {

    private static final String TIPO_EXTRACCION = "EXTRACCION";

    private static final Map<String, Set<String>> TRANSICIONES = Map.of(
            Estados.INTERVENCION_PENDIENTE,
            Set.of(Estados.INTERVENCION_PLANIFICADA,
                    Estados.INTERVENCION_CANCELADA),
            Estados.INTERVENCION_PLANIFICADA,
            Set.of(Estados.INTERVENCION_EN_EJECUCION,
                    Estados.INTERVENCION_REALIZADA,
                    Estados.INTERVENCION_CANCELADA),
            Estados.INTERVENCION_EN_EJECUCION,
            Set.of(Estados.INTERVENCION_REALIZADA,
                    Estados.INTERVENCION_CANCELADA)
    );

    private final IntervencionRepository intervencionRepository;
    private final DemandaRepository demandaRepository;
    private final EjemplarRepository ejemplarRepository;
    private final TipoIntervencionRepository tipoIntervencionRepository;
    private final DemandaEjemplarRepository demandaEjemplarRepository;
    private final HistorialService historialService;

    public IntervencionService(
            IntervencionRepository intervencionRepository,
            DemandaRepository demandaRepository,
            EjemplarRepository ejemplarRepository,
            TipoIntervencionRepository tipoIntervencionRepository,
            DemandaEjemplarRepository demandaEjemplarRepository,
            HistorialService historialService) {

        this.intervencionRepository = intervencionRepository;
        this.demandaRepository = demandaRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.tipoIntervencionRepository = tipoIntervencionRepository;
        this.demandaEjemplarRepository = demandaEjemplarRepository;
        this.historialService = historialService;
    }

    public List<Intervencion> listarTodas() {
        return intervencionRepository.findAll();
    }

    public List<Intervencion> listarPorDemanda(Integer demandaId) {
        return intervencionRepository.findByDemanda_Id(demandaId);
    }

    public Intervencion buscarPorId(Integer id) {
        return intervencionRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Intervención no encontrada"));
    }

    @Transactional
    public Intervencion guardar(Intervencion intervencion) {

        Demanda demanda = demandaRepository.findById(
                intervencion.getDemanda().getId()
        ).orElseThrow(() ->
                new RecursoNoEncontradoException("Demanda no encontrada"));

        Ejemplar ejemplar = ejemplarRepository.findById(
                intervencion.getEjemplar().getId()
        ).orElseThrow(() ->
                new RecursoNoEncontradoException("Ejemplar no encontrado"));

        TipoIntervencion tipo = tipoIntervencionRepository.findById(
                intervencion.getTipoIntervencion().getId()
        ).orElseThrow(() ->
                new RecursoNoEncontradoException("Tipo de intervención no encontrado"));

        verificarDemandaAbierta(demanda);

        DemandaEjemplarId idAsociacion = new DemandaEjemplarId();
        idAsociacion.setDemandaId(demanda.getId());
        idAsociacion.setEjemplarId(ejemplar.getId());

        if (!demandaEjemplarRepository.existsById(idAsociacion)) {
            throw new ReglaNegocioException(
                    "El ejemplar no está asociado a la demanda");
        }

        if (TIPO_EXTRACCION.equals(tipo.getNombre())
                && !Estados.EJEMPLAR_ACTIVO.equals(ejemplar.getEstado())) {
            throw new ReglaNegocioException(
                    "El ejemplar ya fue extraído");
        }

        intervencion.setDemanda(demanda);
        intervencion.setEjemplar(ejemplar);
        intervencion.setTipoIntervencion(tipo);
        intervencion.setEstado(Estados.INTERVENCION_PENDIENTE);
        intervencion.setFechaEjecucion(null);

        if (intervencion.getIntervencionPrevia() != null
                && intervencion.getIntervencionPrevia().getId() != null) {

            Intervencion previa = intervencionRepository.findById(
                    intervencion.getIntervencionPrevia().getId()
            ).orElseThrow(() ->
                    new RecursoNoEncontradoException("Intervención previa no encontrada"));

            if (!previa.getDemanda().getId().equals(demanda.getId())
                    || !previa.getEjemplar().getId().equals(ejemplar.getId())) {
                throw new ReglaNegocioException(
                        "La intervención previa debe ser de la misma demanda y del mismo ejemplar");
            }

            if (Estados.INTERVENCION_CANCELADA.equals(previa.getEstado())) {
                throw new ReglaNegocioException(
                        "La intervención previa está cancelada");
            }

            intervencion.setIntervencionPrevia(previa);
        } else {
            intervencion.setIntervencionPrevia(null);
        }

        Intervencion guardada = intervencionRepository.save(intervencion);

        historialService.registrar(
                HistorialService.INTERVENCION,
                guardada.getId(),
                null,
                Estados.INTERVENCION_PENDIENTE,
                guardada.getId(),
                "Intervención registrada");

        if (Estados.DEMANDA_INGRESADA.equals(demanda.getEstado())) {
            demanda.setEstado(Estados.DEMANDA_EN_GESTION);
            demandaRepository.save(demanda);

            historialService.registrar(
                    HistorialService.DEMANDA,
                    demanda.getId(),
                    Estados.DEMANDA_INGRESADA,
                    Estados.DEMANDA_EN_GESTION,
                    guardada.getId(),
                    "Se registró la primera intervención");
        }

        return guardada;
    }

    @Transactional
    public Intervencion cambiarEstado(
            Integer id,
            CambioEstadoIntervencionRequest request) {

        Intervencion intervencion = buscarPorId(id);

        verificarDemandaAbierta(intervencion.getDemanda());

        String nuevo = request.getEstado().trim().toUpperCase();
        String actual = intervencion.getEstado();

        if (!Estados.ESTADOS_INTERVENCION.contains(nuevo)) {
            throw new ReglaNegocioException("El estado indicado no es válido");
        }

        if (!TRANSICIONES.getOrDefault(actual, Set.of()).contains(nuevo)) {
            throw new ReglaNegocioException(
                    "No se puede pasar una intervención de " + actual
                            + " a " + nuevo);
        }

        Intervencion previa = intervencion.getIntervencionPrevia();

        boolean ejecuta = Estados.INTERVENCION_EN_EJECUCION.equals(nuevo)
                || Estados.INTERVENCION_REALIZADA.equals(nuevo);

        if (ejecuta && previa != null
                && !Estados.INTERVENCION_REALIZADA.equals(previa.getEstado())) {
            throw new ReglaNegocioException(
                    "La intervención previa todavía no fue realizada");
        }

        if (Estados.INTERVENCION_PLANIFICADA.equals(nuevo)) {
            LocalDate fecha = request.getFechaPlanificada() != null
                    ? request.getFechaPlanificada()
                    : intervencion.getFechaPlanificada();

            if (fecha == null) {
                throw new ReglaNegocioException(
                        "Para planificar hay que indicar la fecha prevista");
            }

            intervencion.setFechaPlanificada(fecha);
        }

        boolean extrae = false;

        if (Estados.INTERVENCION_REALIZADA.equals(nuevo)) {
            intervencion.setFechaEjecucion(
                    request.getFechaEjecucion() != null
                            ? request.getFechaEjecucion()
                            : LocalDate.now());

            extrae = TIPO_EXTRACCION.equals(
                    intervencion.getTipoIntervencion().getNombre());

            if (extrae && !Estados.EJEMPLAR_ACTIVO.equals(
                    intervencion.getEjemplar().getEstado())) {
                throw new ReglaNegocioException("El ejemplar ya fue extraído");
            }
        }

        intervencion.setEstado(nuevo);

        Intervencion guardada = intervencionRepository.save(intervencion);

        historialService.registrar(
                HistorialService.INTERVENCION,
                guardada.getId(),
                actual,
                nuevo,
                guardada.getId(),
                request.getObservacion());

        if (extrae) {
            Ejemplar ejemplar = guardada.getEjemplar();
            ejemplar.setEstado(Estados.EJEMPLAR_EXTRAIDO);
            ejemplarRepository.save(ejemplar);

            historialService.registrar(
                    HistorialService.EJEMPLAR,
                    ejemplar.getId(),
                    Estados.EJEMPLAR_ACTIVO,
                    Estados.EJEMPLAR_EXTRAIDO,
                    guardada.getId(),
                    "Extracción realizada");
        }

        return guardada;
    }

    private void verificarDemandaAbierta(Demanda demanda) {
        if (Estados.DEMANDA_CANCELADA.equals(demanda.getEstado())
                || Estados.DEMANDA_CERTIFICADA.equals(demanda.getEstado())) {
            throw new ReglaNegocioException(
                    "La demanda está " + demanda.getEstado().toLowerCase()
                            + " y no admite cambios en sus intervenciones");
        }
    }
}
