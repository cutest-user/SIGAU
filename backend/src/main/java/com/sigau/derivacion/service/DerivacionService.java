package com.sigau.derivacion.service;

import com.sigau.common.Estados;
import com.sigau.common.Textos;
import com.sigau.common.exception.RecursoNoEncontradoException;
import com.sigau.common.exception.ReglaNegocioException;
import com.sigau.config.UsuarioActual;
import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.derivacion.Derivacion;
import com.sigau.derivacion.dto.ActualizarDerivacionRequest;
import com.sigau.derivacion.repository.DerivacionRepository;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DerivacionService {

    private final DerivacionRepository derivacionRepository;
    private final DemandaRepository demandaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioActual usuarioActual;

    public DerivacionService(
            DerivacionRepository derivacionRepository,
            DemandaRepository demandaRepository,
            UsuarioRepository usuarioRepository,
            UsuarioActual usuarioActual) {

        this.derivacionRepository = derivacionRepository;
        this.demandaRepository = demandaRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioActual = usuarioActual;
    }

    public List<Derivacion> listarTodas() {
        return derivacionRepository.findAll();
    }

    public Derivacion buscarPorId(Integer id) {
        return derivacionRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Derivación no encontrada"));
    }

    public Derivacion guardar(Derivacion derivacion) {

        Demanda demanda = demandaRepository.findById(
                derivacion.getDemanda().getId()
        ).orElseThrow(() ->
                new RecursoNoEncontradoException("Demanda no encontrada"));

        Usuario usuario = usuarioRepository.findById(
                usuarioActual.id()
        ).orElseThrow(() ->
                new RecursoNoEncontradoException("Usuario no encontrado"));

        String sistema = derivacion.getSistemaDestino().trim().toUpperCase();

        if (!Estados.SISTEMAS_DESTINO.contains(sistema)) {
            throw new ReglaNegocioException(
                    "El sistema de destino debe ser 147, SAP o Arbopedia");
        }

        if (derivacion.getFecha() == null) {
            derivacion.setFecha(LocalDate.now());
        }

        if (Textos.limpiar(derivacion.getEstado()) == null) {
            derivacion.setEstado(Estados.DERIVACION_PENDIENTE);
        }

        derivacion.setSistemaDestino(sistema);
        derivacion.setReferenciaExterna(
                Textos.limpiar(derivacion.getReferenciaExterna()));
        derivacion.setDemanda(demanda);
        derivacion.setUsuario(usuario);

        return derivacionRepository.save(derivacion);
    }

    public Derivacion actualizarEstado(
            Integer id, ActualizarDerivacionRequest request) {

        Derivacion derivacion = buscarPorId(id);

        derivacion.setEstado(request.getEstado().trim().toUpperCase());
        derivacion.setReferenciaExterna(
                Textos.limpiar(request.getReferenciaExterna()));

        return derivacionRepository.save(derivacion);
    }
}
