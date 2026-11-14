package com.sigau.common.controller;

import com.sigau.common.Ubicacion;
import com.sigau.common.dto.UbicacionRequest;
import com.sigau.common.service.UbicacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @GetMapping
    public List<Ubicacion> listarTodas() {
        return ubicacionService.listarTodas();
    }

    @GetMapping("/{id}")
    public Ubicacion buscarPorId(@PathVariable Integer id) {
        return ubicacionService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Ubicacion> crear(
            @Valid @RequestBody UbicacionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ubicacionService.guardar(request));
    }
}
