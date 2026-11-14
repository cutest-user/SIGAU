package com.sigau.verificacion.service;

import com.sigau.common.Estados;
import com.sigau.common.exception.RecursoNoEncontradoException;
import com.sigau.common.exception.ReglaNegocioException;
import com.sigau.config.UsuarioActual;
import com.sigau.intervencion.Intervencion;
import com.sigau.intervencion.repository.IntervencionRepository;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.repository.UsuarioRepository;
import com.sigau.verificacion.Verificacion;
import com.sigau.verificacion.repository.VerificacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VerificacionService {

    private final VerificacionRepository verificacionRepository;
    private final IntervencionRepository intervencionRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioActual usuarioActual;

    public VerificacionService(
            VerificacionRepository verificacionRepository,
            IntervencionRepository intervencionRepository,
            UsuarioRepository usuarioRepository,
            UsuarioActual usuarioActual) {
        this.verificacionRepository = verificacionRepository;
        this.intervencionRepository = intervencionRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioActual = usuarioActual;
    }

    public List<Verificacion> listarTodas() {
        return verificacionRepository.findAll();
    }

    public Verificacion buscarPorId(Integer id) {
        return verificacionRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Verificación no encontrada"));
    }

    public Verificacion guardar(Verificacion verificacion) {

        Intervencion intervencion = intervencionRepository
                .findById(verificacion.getIntervencion().getId())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Intervención no encontrada"));

        Usuario usuario = usuarioRepository
                .findById(usuarioActual.id())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Usuario no encontrado"));

        if (!Estados.INTERVENCION_REALIZADA.equals(intervencion.getEstado())) {
            throw new ReglaNegocioException(
                    "Solo se puede verificar una intervención realizada");
        }

        String resultado = verificacion.getResultado().trim().toUpperCase();

        if (!Estados.RESULTADOS_VERIFICACION.contains(resultado)) {
            throw new ReglaNegocioException(
                    "El resultado debe ser APROBADA u OBSERVADA");
        }

        if (verificacion.getFecha() == null) {
            verificacion.setFecha(LocalDate.now());
        }

        verificacion.setResultado(resultado);
        verificacion.setIntervencion(intervencion);
        verificacion.setUsuario(usuario);

        return verificacionRepository.save(verificacion);
    }
}

