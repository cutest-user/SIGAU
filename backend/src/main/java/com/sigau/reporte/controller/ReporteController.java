
package com.sigau.reporte.controller;

import com.sigau.reporte.dto.ReporteResponseDTO;
import com.sigau.reporte.service.ReporteService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ReporteResponseDTO obtenerReportes() {
        return reporteService.generarReporte();
    }
}
