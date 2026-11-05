
package com.sigau.ejemplar.service;

import com.sigau.common.Ubicacion;
import com.sigau.common.repository.UbicacionRepository;
import com.sigau.ejemplar.Ejemplar;
import com.sigau.ejemplar.repository.EjemplarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EjemplarService {

    private final EjemplarRepository ejemplarRepository;
    private final UbicacionRepository ubicacionRepository;

    public EjemplarService(
            EjemplarRepository ejemplarRepository,
            UbicacionRepository ubicacionRepository) {
        this.ejemplarRepository = ejemplarRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<Ejemplar> listarTodos() {
        return ejemplarRepository.findAll();
    }

    public Optional<Ejemplar> buscarPorId(Integer id) {
        return ejemplarRepository.findById(id);
    }

    public Ejemplar guardar(Ejemplar ejemplar) {

        Integer ubicacionId = ejemplar.getUbicacion().getId();

        Ubicacion ubicacion = ubicacionRepository.findById(ubicacionId)
                .orElseThrow(() ->
                        new RuntimeException("Ubicación no encontrada"));

        ejemplar.setUbicacion(ubicacion);
        ejemplar.setFechaAlta(LocalDateTime.now());

        return ejemplarRepository.save(ejemplar);
    }
}


