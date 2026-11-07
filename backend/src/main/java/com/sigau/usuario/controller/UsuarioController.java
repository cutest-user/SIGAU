package com.sigau.usuario.controller;
import jakarta.validation.Valid;
import com.sigau.usuario.dto.UsuarioRequestDTO;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.dto.UsuarioResponseDTO;
import com.sigau.usuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(
            @Valid @RequestBody UsuarioRequestDTO request) {

        UsuarioResponseDTO respuesta = usuarioService.crear(request);

        return ResponseEntity.status(201).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioRequestDTO request) {

        return ResponseEntity.ok(
                usuarioService.actualizar(id, request)
        );
    }
}