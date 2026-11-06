
package com.sigau.seguimiento.controller;

import com.sigau.relacion.demandaejemplar.DemandaEjemplar;
import com.sigau.seguimiento.dto.SeguimientoHistorialDTO;
import com.sigau.seguimiento.service.SeguimientoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seguimiento")
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    public SeguimientoController(SeguimientoService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    @GetMapping("/ejemplar/{ejemplarId}/demandas")
    public List<DemandaEjemplar> buscarDemandasPorEjemplar(
            @PathVariable Integer ejemplarId) {
        return seguimientoService.buscarDemandasPorEjemplar(ejemplarId);
    }
    @GetMapping("/ejemplar/{ejemplarId}/historial")
    public SeguimientoHistorialDTO obtenerHistorial(
            @PathVariable Integer ejemplarId) {

        return seguimientoService.obtenerHistorial(ejemplarId);
    }
}

