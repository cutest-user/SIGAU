
package com.sigau.verificacion.service;

import com.sigau.intervencion.Intervencion;
import com.sigau.intervencion.repository.IntervencionRepository;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.repository.UsuarioRepository;
import com.sigau.verificacion.Verificacion;
import com.sigau.verificacion.repository.VerificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerificacionService {

    private final VerificacionRepository verificacionRepository;
    private final IntervencionRepository intervencionRepository;
    private final UsuarioRepository usuarioRepository;

    public VerificacionService(
            VerificacionRepository verificacionRepository,
            IntervencionRepository intervencionRepository,
            UsuarioRepository usuarioRepository) {
        this.verificacionRepository = verificacionRepository;
        this.intervencionRepository = intervencionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Verificacion> listarTodas() {
        return verificacionRepository.findAll();
    }

    public Verificacion buscarPorId(Integer id) {
        return verificacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Verificación no encontrada"));
    }

    public Verificacion guardar(Verificacion verificacion) {

        Intervencion intervencion = intervencionRepository
                .findById(verificacion.getIntervencion().getId())
                .orElseThrow(() ->
                        new RuntimeException("Intervención no encontrada"));

        Usuario usuario = usuarioRepository
                .findById(verificacion.getUsuario().getId())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        verificacion.setIntervencion(intervencion);
        verificacion.setUsuario(usuario);

        return verificacionRepository.save(verificacion);
    }
}

