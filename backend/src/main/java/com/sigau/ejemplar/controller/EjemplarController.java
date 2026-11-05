
package com.sigau.ejemplar.controller;

import com.sigau.ejemplar.Ejemplar;
import com.sigau.ejemplar.service.EjemplarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejemplares")
public class EjemplarController {

    private final EjemplarService ejemplarService;

    public EjemplarController(EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
    }

    @GetMapping
    public List<Ejemplar> listarTodos() {
        return ejemplarService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ejemplar> buscarPorId(@PathVariable Integer id) {
        return ejemplarService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ejemplar> crear(@RequestBody Ejemplar ejemplar) {
        Ejemplar ejemplarGuardado = ejemplarService.guardar(ejemplar);
        return ResponseEntity.status(HttpStatus.CREATED).body(ejemplarGuardado);
    }
}


