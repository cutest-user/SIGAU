package com.sigau.demanda.controller;

import com.sigau.demanda.OrigenDemanda;
import com.sigau.demanda.service.OrigenDemandaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/origenes-demanda")
public class OrigenDemandaController {

    private final OrigenDemandaService origenDemandaService;

    public OrigenDemandaController(OrigenDemandaService origenDemandaService) {
        this.origenDemandaService = origenDemandaService;
    }

    @GetMapping
    public List<OrigenDemanda> listarTodos() {
        return origenDemandaService.listarTodos();
    }
}
