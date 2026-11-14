package com.sigau.inspeccion.service;

import com.sigau.common.Estados;
import com.sigau.common.Textos;
import com.sigau.common.exception.RecursoNoEncontradoException;
import com.sigau.config.UsuarioActual;
import com.sigau.ejemplar.Ejemplar;
import com.sigau.ejemplar.repository.EjemplarRepository;
import com.sigau.inspeccion.Inspeccion;
import com.sigau.inspeccion.repository.InspeccionRepository;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InspeccionService {

    private final InspeccionRepository inspeccionRepository;
    private final EjemplarRepository ejemplarRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioActual usuarioActual;

    public InspeccionService(
            InspeccionRepository inspeccionRepository,
            EjemplarRepository ejemplarRepository,
            UsuarioRepository usuarioRepository,
            UsuarioActual usuarioActual) {
        this.inspeccionRepository = inspeccionRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioActual = usuarioActual;
    }

    public List<Inspeccion> listarTodas() {
        return inspeccionRepository.findAll();
    }

    public Optional<Inspeccion> buscarPorId(Integer id) {
        return inspeccionRepository.findById(id);
    }

    public Inspeccion guardar(Inspeccion inspeccion) {
        Integer ejemplarId = inspeccion.getEjemplar().getId();
        Integer inspectorId = usuarioActual.id();

        Ejemplar ejemplar = ejemplarRepository.findById(ejemplarId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Ejemplar no encontrado"));

        Usuario inspector = usuarioRepository.findById(inspectorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inspector no encontrado"));

        inspeccion.setEjemplar(ejemplar);
        inspeccion.setInspector(inspector);

        if (inspeccion.getFecha() == null) {
            inspeccion.setFecha(LocalDate.now());
        }

        if (Textos.limpiar(inspeccion.getEstado()) == null) {
            inspeccion.setEstado(Estados.INSPECCION_REALIZADA);
        }

        inspeccion.setEspecieIdentificada(
                Textos.limpiar(inspeccion.getEspecieIdentificada()));

        return inspeccionRepository.save(inspeccion);
    }
}
