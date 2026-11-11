
package com.sigau.inspeccion.controller;

import com.sigau.inspeccion.EvidenciaInspeccion;
import com.sigau.inspeccion.dto.CrearEvidenciaInspeccionRequest;
import com.sigau.inspeccion.service.EvidenciaInspeccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evidencias-inspeccion")
public class EvidenciaInspeccionController {

    private final EvidenciaInspeccionService evidenciaService;

    public EvidenciaInspeccionController(
            EvidenciaInspeccionService evidenciaService) {
        this.evidenciaService = evidenciaService;
    }

    @PostMapping
    public ResponseEntity<EvidenciaInspeccion> guardar(
            @RequestBody CrearEvidenciaInspeccionRequest request) {

        EvidenciaInspeccion evidencia = evidenciaService.guardar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(evidencia);
    }

    @GetMapping("/inspeccion/{inspeccionId}")
    public List<EvidenciaInspeccion> listarPorInspeccion(
            @PathVariable Integer inspeccionId) {

        return evidenciaService.listarPorInspeccion(inspeccionId);
    }
}
