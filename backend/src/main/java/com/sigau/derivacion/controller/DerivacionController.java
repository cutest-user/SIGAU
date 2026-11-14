package com.sigau.derivacion.controller;

import com.sigau.derivacion.Derivacion;
import com.sigau.derivacion.dto.ActualizarDerivacionRequest;
import com.sigau.derivacion.service.DerivacionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/derivaciones")
public class DerivacionController {

    private final DerivacionService derivacionService;

    public DerivacionController(DerivacionService derivacionService) {
        this.derivacionService = derivacionService;
    }

    @GetMapping
    public List<Derivacion> listarTodas() {
        return derivacionService.listarTodas();
    }

    @GetMapping("/{id}")
    public Derivacion buscarPorId(@PathVariable Integer id) {
        return derivacionService.buscarPorId(id);
    }

    @PostMapping
    public Derivacion guardar(@Valid @RequestBody Derivacion derivacion) {
        return derivacionService.guardar(derivacion);
    }

    @PatchMapping("/{id}/estado")
    public Derivacion actualizarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody ActualizarDerivacionRequest request) {

        return derivacionService.actualizarEstado(id, request);
    }
}
