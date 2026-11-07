package com.sigau.usuario.service;

import com.sigau.common.exception.RecursoDuplicadoException;
import com.sigau.common.exception.RecursoNoEncontradoException;
import com.sigau.usuario.dto.UsuarioRequestDTO;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.dto.UsuarioResponseDTO;
import com.sigau.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró el usuario con ID: " + id
                        )
                );

        return convertirADTO(usuario);
    }

    public Usuario guardar(Usuario usuario) {
        usuario.setPasswordHash(
                passwordEncoder.encode(usuario.getPasswordHash())
        );

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    private UsuarioResponseDTO convertirADTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getRolId(),
                usuario.getActivo()
        );
    }
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RecursoDuplicadoException(
                    "Ya existe un usuario registrado con ese email."
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );
        usuario.setRolId(request.getRolId());
        usuario.setActivo(request.getActivo());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return convertirADTO(usuarioGuardado);
    }
    public UsuarioResponseDTO actualizar(
            Integer id,
            UsuarioRequestDTO request) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró el usuario con ID: " + id
                        )
                );

        if (usuarioRepository.existsByEmailAndIdNot(
                request.getEmail(), id)) {

            throw new RecursoDuplicadoException(
                    "El email ya está registrado por otro usuario."
            );
        }

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setRolId(request.getRolId());

        if (request.getActivo() != null) {
            usuario.setActivo(request.getActivo());
        }

        if (request.getPassword() != null
                && !request.getPassword().isBlank()) {

            usuario.setPasswordHash(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        Usuario actualizado = usuarioRepository.save(usuario);

        return convertirADTO(actualizado);
    }
}