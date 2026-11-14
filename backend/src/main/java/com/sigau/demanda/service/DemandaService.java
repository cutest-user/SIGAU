package com.sigau.demanda.service;

import com.sigau.common.Estados;
import com.sigau.common.Ubicacion;
import com.sigau.common.exception.RecursoNoEncontradoException;
import com.sigau.common.exception.ReglaNegocioException;
import com.sigau.common.repository.UbicacionRepository;
import com.sigau.config.UsuarioActual;
import com.sigau.demanda.Demanda;
import com.sigau.demanda.dto.ActualizarDemandaRequest;
import com.sigau.demanda.dto.CrearDemandaRequest;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.demanda.repository.OrigenDemandaRepository;
import com.sigau.historial.service.HistorialService;
import com.sigau.intervencion.repository.IntervencionRepository;
import com.sigau.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DemandaService {

    private final DemandaRepository demandaRepository;
    private final OrigenDemandaRepository origenDemandaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioActual usuarioActual;
    private final UbicacionRepository ubicacionRepository;
    private final IntervencionRepository intervencionRepository;
    private final HistorialService historialService;

    public DemandaService(
            DemandaRepository demandaRepository,
            OrigenDemandaRepository origenDemandaRepository,
            UsuarioRepository usuarioRepository,
            UbicacionRepository ubicacionRepository,
            IntervencionRepository intervencionRepository,
            HistorialService historialService,
            UsuarioActual usuarioActual) {

        this.demandaRepository = demandaRepository;
        this.origenDemandaRepository = origenDemandaRepository;
        this.usuarioRepository = usuarioRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.intervencionRepository = intervencionRepository;
        this.historialService = historialService;
        this.usuarioActual = usuarioActual;
    }

    public List<Demanda> listarTodas() {
        return demandaRepository.findAll();
    }

    public Optional<Demanda> buscarPorId(Integer id) {
        return demandaRepository.findById(id);
    }

    @Transactional
    public Demanda guardar(CrearDemandaRequest request) {

        Demanda demanda = new Demanda();

        demanda.setOrigen(
                origenDemandaRepository.findById(request.getOrigenId())
                        .orElseThrow(() -> new RecursoNoEncontradoException("Origen de demanda no encontrado"))
        );

        demanda.setUsuario(
                usuarioRepository.findById(usuarioActual.id())
                        .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"))
        );

        if (request.getUbicacionId() != null) {
            Ubicacion ubicacion = ubicacionRepository.findById(request.getUbicacionId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Ubicación no encontrada"));

            demanda.setUbicacion(ubicacion);
        }

        demanda.setFecha(LocalDateTime.now());
        demanda.setEstado(Estados.DEMANDA_INGRESADA);
        demanda.setMotivo(request.getMotivo());
        demanda.setDescripcion(request.getDescripcion());
        demanda.setPrioridad(
                request.getPrioridad() != null ? request.getPrioridad() : "MEDIA"
        );
        demanda.setObservaciones(request.getObservaciones());

        Demanda guardada = demandaRepository.save(demanda);

        historialService.registrar(
                HistorialService.DEMANDA,
                guardada.getId(),
                null,
                Estados.DEMANDA_INGRESADA,
                null,
                "Demanda ingresada");

        return guardada;
    }

    public Optional<Demanda> actualizar(Integer id, ActualizarDemandaRequest request) {

        Optional<Demanda> resultado = demandaRepository.findById(id);

        if (resultado.isEmpty()) {
            return Optional.empty();
        }

        Demanda demanda = resultado.get();

        verificarAbierta(demanda);

        demanda.setOrigen(
                origenDemandaRepository.findById(request.getOrigenId())
                        .orElseThrow(() -> new RecursoNoEncontradoException("Origen de demanda no encontrado"))
        );

        if (request.getUbicacionId() != null) {
            Ubicacion ubicacion = ubicacionRepository.findById(request.getUbicacionId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Ubicación no encontrada"));

            demanda.setUbicacion(ubicacion);
        } else {
            demanda.setUbicacion(null);
        }

        demanda.setMotivo(request.getMotivo());
        demanda.setDescripcion(request.getDescripcion());
        if (request.getPrioridad() != null) {
            demanda.setPrioridad(request.getPrioridad());
        }

        demanda.setObservaciones(request.getObservaciones());

        return Optional.of(demandaRepository.save(demanda));
    }

    public List<Demanda> buscarPosiblesDuplicadas(Integer ubicacionId) {
        return demandaRepository.findByUbicacion_IdAndEstadoIn(
                ubicacionId,
                List.of(Estados.DEMANDA_INGRESADA, Estados.DEMANDA_EN_GESTION));
    }

    @Transactional
    public Demanda cancelar(Integer id, String observacion) {

        Demanda demanda = demandaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Demanda no encontrada"));

        String anterior = demanda.getEstado();

        if (!Estados.DEMANDA_INGRESADA.equals(anterior)
                && !Estados.DEMANDA_EN_GESTION.equals(anterior)) {
            throw new ReglaNegocioException(
                    "Solo se puede cancelar una demanda ingresada o en gestión");
        }

        boolean conIntervencionesActivas = intervencionRepository
                .findByDemanda_Id(id)
                .stream()
                .anyMatch(i -> !Estados.INTERVENCION_CANCELADA.equals(i.getEstado()));

        if (conIntervencionesActivas) {
            throw new ReglaNegocioException(
                    "Hay que cancelar primero las intervenciones de la demanda");
        }

        demanda.setEstado(Estados.DEMANDA_CANCELADA);
        demanda.setFechaCierre(LocalDateTime.now());

        Demanda guardada = demandaRepository.save(demanda);

        historialService.registrar(
                HistorialService.DEMANDA,
                id,
                anterior,
                Estados.DEMANDA_CANCELADA,
                null,
                observacion);

        return guardada;
    }

    private void verificarAbierta(Demanda demanda) {
        if (Estados.DEMANDA_CANCELADA.equals(demanda.getEstado())
                || Estados.DEMANDA_CERTIFICADA.equals(demanda.getEstado())) {
            throw new ReglaNegocioException(
                    "La demanda está " + demanda.getEstado().toLowerCase()
                            + " y no puede modificarse");
        }
    }

}
