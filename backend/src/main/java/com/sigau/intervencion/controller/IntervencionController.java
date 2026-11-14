package com.sigau.intervencion.controller;

import com.sigau.intervencion.Intervencion;
import com.sigau.intervencion.dto.CambioEstadoIntervencionRequest;
import com.sigau.intervencion.service.IntervencionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intervenciones")
public class IntervencionController {

    private final IntervencionService intervencionService;

    public IntervencionController(IntervencionService intervencionService) {
        this.intervencionService = intervencionService;
    }

    @GetMapping
    public List<Intervencion> listar(
            @RequestParam(required = false) Integer demandaId) {

        if (demandaId != null) {
            return intervencionService.listarPorDemanda(demandaId);
        }

        return intervencionService.listarTodas();
    }

    @GetMapping("/{id}")
    public Intervencion buscarPorId(@PathVariable Integer id) {
        return intervencionService.buscarPorId(id);
    }

    @PostMapping
    public Intervencion guardar(@Valid @RequestBody Intervencion intervencion) {
        return intervencionService.guardar(intervencion);
    }

    @PatchMapping("/{id}/estado")
    public Intervencion cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody CambioEstadoIntervencionRequest request) {

        return intervencionService.cambiarEstado(id, request);
    }
}
