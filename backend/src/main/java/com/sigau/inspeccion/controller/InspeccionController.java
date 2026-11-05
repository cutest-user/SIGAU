package com.sigau.inspeccion.controller;

import com.sigau.inspeccion.Inspeccion;
import com.sigau.inspeccion.service.InspeccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inspecciones")
public class InspeccionController {

    private final InspeccionService inspeccionService;

    public InspeccionController(InspeccionService inspeccionService) {
        this.inspeccionService = inspeccionService;
    }

    @GetMapping
    public List<Inspeccion> listarTodas() {
        return inspeccionService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inspeccion> buscarPorId(@PathVariable Integer id) {
        return inspeccionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Inspeccion> guardar(@RequestBody Inspeccion inspeccion) {
        Inspeccion nuevaInspeccion = inspeccionService.guardar(inspeccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInspeccion);
    }
}
