
package com.sigau.certificacion.controller;

import com.sigau.certificacion.Certificacion;
import com.sigau.certificacion.service.CertificacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificaciones")
public class CertificacionController {

    private final CertificacionService certificacionService;

    public CertificacionController(CertificacionService certificacionService) {
        this.certificacionService = certificacionService;
    }

    @GetMapping
    public List<Certificacion> listarTodas() {
        return certificacionService.listarTodas();
    }

    @GetMapping("/{id}")
    public Certificacion buscarPorId(@PathVariable String id) {
        return certificacionService.buscarPorId(id);
    }

    @PostMapping
    public Certificacion guardar(@RequestBody Certificacion certificacion) {
        return certificacionService.guardar(certificacion);
    }
}

