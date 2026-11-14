package com.sigau.historial.service;

import com.sigau.common.Textos;
import com.sigau.common.exception.ReglaNegocioException;
import com.sigau.config.UsuarioActual;
import com.sigau.historial.Historial;
import com.sigau.historial.repository.HistorialRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistorialService {

    public static final String DEMANDA = "DEMANDA";
    public static final String EJEMPLAR = "EJEMPLAR";
    public static final String INTERVENCION = "INTERVENCION";

    private final HistorialRepository historialRepository;
    private final UsuarioActual usuarioActual;

    public HistorialService(
            HistorialRepository historialRepository,
            UsuarioActual usuarioActual) {
        this.historialRepository = historialRepository;
        this.usuarioActual = usuarioActual;
    }

    public Historial registrar(
            String entidad,
            Integer entidadId,
            String estadoAnterior,
            String estadoNuevo,
            Integer intervencionId,
            String observacion) {

        Historial registro = new Historial();

        registro.setEntidad(entidad);
        registro.setEntidadId(entidadId);
        registro.setEstadoAnterior(estadoAnterior);
        registro.setEstadoNuevo(estadoNuevo);
        registro.setIntervencionId(intervencionId);
        registro.setObservacion(Textos.limpiar(observacion));
        registro.setUsuarioId(usuarioActual.id());
        registro.setFecha(LocalDate.now());

        return historialRepository.save(registro);
    }

    public List<Historial> listar(String entidad, Integer entidadId) {
        String tipo = entidad.trim().toUpperCase();

        if (!List.of(DEMANDA, EJEMPLAR, INTERVENCION).contains(tipo)) {
            throw new ReglaNegocioException(
                    "La entidad debe ser DEMANDA, EJEMPLAR o INTERVENCION");
        }

        return historialRepository
                .findByEntidadAndEntidadIdOrderByIdAsc(tipo, entidadId);
    }
}
