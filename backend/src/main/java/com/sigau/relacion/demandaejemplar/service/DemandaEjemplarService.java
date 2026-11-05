
package com.sigau.relacion.demandaejemplar.service;

import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.ejemplar.Ejemplar;
import com.sigau.ejemplar.repository.EjemplarRepository;
import com.sigau.relacion.demandaejemplar.DemandaEjemplar;
import com.sigau.relacion.demandaejemplar.DemandaEjemplarId;
import com.sigau.relacion.demandaejemplar.repository.DemandaEjemplarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DemandaEjemplarService {

    private final DemandaEjemplarRepository demandaEjemplarRepository;
    private final DemandaRepository demandaRepository;
    private final EjemplarRepository ejemplarRepository;

    public DemandaEjemplarService(
            DemandaEjemplarRepository demandaEjemplarRepository,
            DemandaRepository demandaRepository,
            EjemplarRepository ejemplarRepository) {
        this.demandaEjemplarRepository = demandaEjemplarRepository;
        this.demandaRepository = demandaRepository;
        this.ejemplarRepository = ejemplarRepository;
    }

    public DemandaEjemplar asociar(
            Integer demandaId,
            Integer ejemplarId,
            String observacion) {

        Demanda demanda = demandaRepository.findById(demandaId)
                .orElseThrow(() ->
                        new RuntimeException("Demanda no encontrada"));

        Ejemplar ejemplar = ejemplarRepository.findById(ejemplarId)
                .orElseThrow(() ->
                        new RuntimeException("Ejemplar no encontrado"));

        DemandaEjemplarId id = new DemandaEjemplarId();
        id.setDemandaId(demandaId);
        id.setEjemplarId(ejemplarId);

        DemandaEjemplar asociacion = new DemandaEjemplar();
        asociacion.setId(id);
        asociacion.setDemanda(demanda);
        asociacion.setEjemplar(ejemplar);
        asociacion.setFechaAsociacion(LocalDateTime.now());
        asociacion.setObservacion(observacion);

        return demandaEjemplarRepository.save(asociacion);
    }
}


