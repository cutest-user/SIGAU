
package com.sigau.relacion.demandaejemplar.controller;

import com.sigau.relacion.demandaejemplar.DemandaEjemplar;
import com.sigau.relacion.demandaejemplar.dto.CrearDemandaEjemplarRequest;
import com.sigau.relacion.demandaejemplar.service.DemandaEjemplarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demandas-ejemplares")
public class DemandaEjemplarController {

    private final DemandaEjemplarService demandaEjemplarService;

    public DemandaEjemplarController(
            DemandaEjemplarService demandaEjemplarService) {
        this.demandaEjemplarService = demandaEjemplarService;
    }

    @PostMapping
    public ResponseEntity<DemandaEjemplar> asociar(
            @RequestBody CrearDemandaEjemplarRequest request) {

        DemandaEjemplar asociacion = demandaEjemplarService.asociar(
                request.getDemandaId(),
                request.getEjemplarId(),
                request.getObservacion()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(asociacion);
    }
}

