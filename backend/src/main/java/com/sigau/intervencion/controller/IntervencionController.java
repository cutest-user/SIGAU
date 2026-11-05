
package com.sigau.intervencion.controller;

import com.sigau.intervencion.Intervencion;
import com.sigau.intervencion.service.IntervencionService;
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
    public List<Intervencion> listarTodas() {
        return intervencionService.listarTodas();
    }

    @GetMapping("/{id}")
    public Intervencion buscarPorId(@PathVariable Integer id) {
        return intervencionService.buscarPorId(id);
    }

    @PostMapping
    public Intervencion guardar(@RequestBody Intervencion intervencion) {
        return intervencionService.guardar(intervencion);
    }
}

