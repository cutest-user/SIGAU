package com.sigau.intervencion.service;

import com.sigau.intervencion.TipoIntervencion;
import com.sigau.intervencion.repository.TipoIntervencionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoIntervencionService {

    private final TipoIntervencionRepository tipoIntervencionRepository;

    public TipoIntervencionService(
            TipoIntervencionRepository tipoIntervencionRepository) {
        this.tipoIntervencionRepository = tipoIntervencionRepository;
    }

    public List<TipoIntervencion> listarTodos() {
        return tipoIntervencionRepository.findAll();
    }
}
