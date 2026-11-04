package com.sigau.demanda.service;

import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandaService {

    private final DemandaRepository demandaRepository;

    public DemandaService(DemandaRepository demandaRepository) {
        this.demandaRepository = demandaRepository;
    }

    public List<Demanda> listarTodas() {
        return demandaRepository.findAll();
    }

    public Optional<Demanda> buscarPorId(Integer id) {
        return demandaRepository.findById(id);
    }

    public Demanda guardar(Demanda demanda) {
        return demandaRepository.save(demanda);
    }
}
