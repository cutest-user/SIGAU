
package com.sigau.verificacion.controller;

import com.sigau.verificacion.Verificacion;
import com.sigau.verificacion.service.VerificacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/verificaciones")
public class VerificacionController {

    private final VerificacionService verificacionService;

    public VerificacionController(VerificacionService verificacionService) {
        this.verificacionService = verificacionService;
    }

    @GetMapping
    public List<Verificacion> listarTodas() {
        return verificacionService.listarTodas();
    }

    @GetMapping("/{id}")
    public Verificacion buscarPorId(@PathVariable Integer id) {
        return verificacionService.buscarPorId(id);
    }

    @PostMapping
    public Verificacion guardar(@RequestBody Verificacion verificacion) {
        return verificacionService.guardar(verificacion);
    }
}

