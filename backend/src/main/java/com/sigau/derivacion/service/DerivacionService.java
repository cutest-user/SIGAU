
package com.sigau.derivacion.service;

import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.derivacion.Derivacion;
import com.sigau.derivacion.repository.DerivacionRepository;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DerivacionService {

    private final DerivacionRepository derivacionRepository;
    private final DemandaRepository demandaRepository;
    private final UsuarioRepository usuarioRepository;

    public DerivacionService(
            DerivacionRepository derivacionRepository,
            DemandaRepository demandaRepository,
            UsuarioRepository usuarioRepository) {

        this.derivacionRepository = derivacionRepository;
        this.demandaRepository = demandaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Derivacion> listarTodas() {
        return derivacionRepository.findAll();
    }

    public Derivacion buscarPorId(Integer id) {
        return derivacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Derivación no encontrada"));
    }

    public Derivacion guardar(Derivacion derivacion) {

        Demanda demanda = demandaRepository.findById(
                derivacion.getDemanda().getId()
        ).orElseThrow(() ->
                new RuntimeException("Demanda no encontrada"));

        Usuario usuario = usuarioRepository.findById(
                derivacion.getUsuario().getId()
        ).orElseThrow(() ->
                new RuntimeException("Usuario no encontrado"));

        derivacion.setDemanda(demanda);
        derivacion.setUsuario(usuario);

        return derivacionRepository.save(derivacion);
    }
}
