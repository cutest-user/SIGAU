package com.sigau.intervencion.controller;

import com.sigau.intervencion.TipoIntervencion;
import com.sigau.intervencion.service.TipoIntervencionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-intervencion")
public class TipoIntervencionController {

    private final TipoIntervencionService tipoIntervencionService;

    public TipoIntervencionController(
            TipoIntervencionService tipoIntervencionService) {
        this.tipoIntervencionService = tipoIntervencionService;
    }

    @GetMapping
    public List<TipoIntervencion> listarTodos() {
        return tipoIntervencionService.listarTodos();
    }
}
