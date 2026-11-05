package com.sigau.demanda.service;

import com.sigau.demanda.OrigenDemanda;
import com.sigau.demanda.repository.OrigenDemandaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrigenDemandaService {

    private final OrigenDemandaRepository origenDemandaRepository;

    public OrigenDemandaService(OrigenDemandaRepository origenDemandaRepository) {
        this.origenDemandaRepository = origenDemandaRepository;
    }

    public List<OrigenDemanda> listarTodos() {
        return origenDemandaRepository.findAll();
    }
}
