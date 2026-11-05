package com.sigau.demanda.service;

import com.sigau.common.Ubicacion;
import com.sigau.common.repository.UbicacionRepository;
import com.sigau.demanda.Demanda;
import com.sigau.demanda.dto.CrearDemandaRequest;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.demanda.repository.OrigenDemandaRepository;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DemandaService {

    private final DemandaRepository demandaRepository;
    private final OrigenDemandaRepository origenDemandaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UbicacionRepository ubicacionRepository;

    public DemandaService(
            DemandaRepository demandaRepository,
            OrigenDemandaRepository origenDemandaRepository,
            UsuarioRepository usuarioRepository,
            UbicacionRepository ubicacionRepository) {

        this.demandaRepository = demandaRepository;
        this.origenDemandaRepository = origenDemandaRepository;
        this.usuarioRepository = usuarioRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<Demanda> listarTodas() {
        return demandaRepository.findAll();
    }

    public Optional<Demanda> buscarPorId(Integer id) {
        return demandaRepository.findById(id);
    }

    public Demanda guardar(CrearDemandaRequest request) {

        Demanda demanda = new Demanda();

        demanda.setOrigen(
                origenDemandaRepository.findById(request.getOrigenId())
                        .orElseThrow(() -> new RuntimeException("Origen de demanda no encontrado"))
        );

        demanda.setUsuario(
                usuarioRepository.findById(request.getUsuarioId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
        );

        if (request.getUbicacionId() != null) {
            Ubicacion ubicacion = ubicacionRepository.findById(request.getUbicacionId())
                    .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

            demanda.setUbicacion(ubicacion);
        }

        demanda.setFecha(LocalDateTime.now());
        demanda.setEstado("INGRESADA");
        demanda.setMotivo(request.getMotivo());
        demanda.setDescripcion(request.getDescripcion());
        demanda.setPrioridad(
                request.getPrioridad() != null ? request.getPrioridad() : "MEDIA"
        );
        demanda.setObservaciones(request.getObservaciones());

        return demandaRepository.save(demanda);
    }
}
