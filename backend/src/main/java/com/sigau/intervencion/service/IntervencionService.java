
package com.sigau.intervencion.service;

import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.ejemplar.Ejemplar;
import com.sigau.ejemplar.repository.EjemplarRepository;
import com.sigau.intervencion.Intervencion;
import com.sigau.intervencion.TipoIntervencion;
import com.sigau.intervencion.repository.IntervencionRepository;
import com.sigau.intervencion.repository.TipoIntervencionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntervencionService {

    private final IntervencionRepository intervencionRepository;
    private final DemandaRepository demandaRepository;
    private final EjemplarRepository ejemplarRepository;
    private final TipoIntervencionRepository tipoIntervencionRepository;

    public IntervencionService(
            IntervencionRepository intervencionRepository,
            DemandaRepository demandaRepository,
            EjemplarRepository ejemplarRepository,
            TipoIntervencionRepository tipoIntervencionRepository) {

        this.intervencionRepository = intervencionRepository;
        this.demandaRepository = demandaRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.tipoIntervencionRepository = tipoIntervencionRepository;
    }

    public List<Intervencion> listarTodas() {
        return intervencionRepository.findAll();
    }

    public Intervencion buscarPorId(Integer id) {
        return intervencionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Intervención no encontrada"));
    }

    public Intervencion guardar(Intervencion intervencion) {

        Demanda demanda = demandaRepository.findById(
                intervencion.getDemanda().getId()
        ).orElseThrow(() ->
                new RuntimeException("Demanda no encontrada"));

        Ejemplar ejemplar = ejemplarRepository.findById(
                intervencion.getEjemplar().getId()
        ).orElseThrow(() ->
                new RuntimeException("Ejemplar no encontrado"));

        TipoIntervencion tipo = tipoIntervencionRepository.findById(
                intervencion.getTipoIntervencion().getId()
        ).orElseThrow(() ->
                new RuntimeException("Tipo de intervención no encontrado"));

        intervencion.setDemanda(demanda);
        intervencion.setEjemplar(ejemplar);
        intervencion.setTipoIntervencion(tipo);

        if (intervencion.getIntervencionPrevia() != null
                && intervencion.getIntervencionPrevia().getId() != null) {

            Intervencion previa = intervencionRepository.findById(
                    intervencion.getIntervencionPrevia().getId()
            ).orElseThrow(() ->
                    new RuntimeException("Intervención previa no encontrada"));

            intervencion.setIntervencionPrevia(previa);
        } else {
            intervencion.setIntervencionPrevia(null);
        }

        return intervencionRepository.save(intervencion);
    }
}

