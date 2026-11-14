package com.sigau.certificacion.service;

import com.sigau.certificacion.Certificacion;
import com.sigau.certificacion.repository.CertificacionRepository;
import com.sigau.common.Estados;
import com.sigau.common.Textos;
import com.sigau.common.exception.RecursoDuplicadoException;
import com.sigau.common.exception.RecursoNoEncontradoException;
import com.sigau.common.exception.ReglaNegocioException;
import com.sigau.config.UsuarioActual;
import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.historial.service.HistorialService;
import com.sigau.intervencion.Intervencion;
import com.sigau.intervencion.repository.IntervencionRepository;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.repository.UsuarioRepository;
import com.sigau.verificacion.repository.VerificacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CertificacionService {

    private final CertificacionRepository certificacionRepository;
    private final DemandaRepository demandaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioActual usuarioActual;
    private final IntervencionRepository intervencionRepository;
    private final VerificacionRepository verificacionRepository;
    private final HistorialService historialService;

    public CertificacionService(
            CertificacionRepository certificacionRepository,
            DemandaRepository demandaRepository,
            UsuarioRepository usuarioRepository,
            IntervencionRepository intervencionRepository,
            VerificacionRepository verificacionRepository,
            HistorialService historialService,
            UsuarioActual usuarioActual) {
        this.certificacionRepository = certificacionRepository;
        this.demandaRepository = demandaRepository;
        this.usuarioRepository = usuarioRepository;
        this.intervencionRepository = intervencionRepository;
        this.verificacionRepository = verificacionRepository;
        this.historialService = historialService;
        this.usuarioActual = usuarioActual;
    }

    public List<Certificacion> listarTodas() {
        return certificacionRepository.findAll();
    }

    public Certificacion buscarPorId(String id) {
        return certificacionRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Certificación no encontrada"));
    }

    @Transactional
    public Certificacion guardar(Certificacion certificacion) {

        Demanda demanda = demandaRepository
                .findById(certificacion.getDemanda().getId())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Demanda no encontrada"));

        Usuario usuario = usuarioRepository
                .findById(usuarioActual.id())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Usuario no encontrado"));

        certificacion.setId(certificacion.getId().trim());

        if (certificacionRepository.existsById(certificacion.getId())) {
            throw new RecursoDuplicadoException(
                    "Ya existe una certificación con ese número de orden");
        }

        if (certificacionRepository.existsByDemandaId(demanda.getId())) {
            throw new RecursoDuplicadoException(
                    "La demanda ya tiene una certificación registrada");
        }

        if (certificacion.getFecha() == null) {
            certificacion.setFecha(LocalDate.now());
        }

        if (Textos.limpiar(certificacion.getEstado()) == null) {
            certificacion.setEstado(Estados.CERTIFICACION_PLANIFICADA);
        }

        verificarCertificable(demanda);

        certificacion.setDemanda(demanda);
        certificacion.setUsuario(usuario);

        Certificacion guardada = certificacionRepository.save(certificacion);

        demanda.setEstado(Estados.DEMANDA_CERTIFICADA);
        demanda.setFechaCierre(LocalDateTime.now());
        demandaRepository.save(demanda);

        historialService.registrar(
                HistorialService.DEMANDA,
                demanda.getId(),
                Estados.DEMANDA_EN_GESTION,
                Estados.DEMANDA_CERTIFICADA,
                null,
                "Certificada con la orden " + guardada.getId());

        return guardada;
    }

    private void verificarCertificable(Demanda demanda) {

        if (!Estados.DEMANDA_EN_GESTION.equals(demanda.getEstado())) {
            throw new ReglaNegocioException(
                    "Solo se puede certificar una demanda en gestión");
        }

        List<Intervencion> intervenciones = intervencionRepository
                .findByDemanda_Id(demanda.getId())
                .stream()
                .filter(i -> !Estados.INTERVENCION_CANCELADA.equals(i.getEstado()))
                .toList();

        if (intervenciones.isEmpty()) {
            throw new ReglaNegocioException(
                    "La demanda no tiene intervenciones para certificar");
        }

        for (Intervencion intervencion : intervenciones) {

            if (!Estados.INTERVENCION_REALIZADA.equals(intervencion.getEstado())) {
                throw new ReglaNegocioException(
                        "Todas las intervenciones deben estar realizadas");
            }

            if (!verificacionRepository.existsByIntervencion_IdAndResultado(
                    intervencion.getId(), Estados.VERIFICACION_APROBADA)) {
                throw new ReglaNegocioException(
                        "Todas las intervenciones deben tener una verificación aprobada");
            }
        }
    }
}

