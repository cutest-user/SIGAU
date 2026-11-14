package com.sigau.historial.controller;

import com.sigau.historial.Historial;
import com.sigau.historial.service.HistorialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
public class HistorialController {

    private final HistorialService historialService;

    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    @GetMapping
    public List<Historial> listar(
            @RequestParam String entidad,
            @RequestParam Integer id) {

        return historialService.listar(entidad, id);
    }
}
