package com.sigau.demanda.controller;

import com.sigau.demanda.Demanda;
import com.sigau.demanda.service.DemandaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandas")
public class DemandaController {

    private final DemandaService demandaService;

    public DemandaController(DemandaService demandaService) {
        this.demandaService = demandaService;
    }

    @GetMapping
    public List<Demanda> listarTodas() {
        return demandaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demanda> buscarPorId(@PathVariable Integer id) {
        return demandaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Demanda> crear(@RequestBody Demanda demanda) {
        Demanda demandaGuardada = demandaService.guardar(demanda);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(demandaGuardada);
    }
}
