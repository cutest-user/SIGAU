
package com.sigau.certificacion.service;

import com.sigau.certificacion.Certificacion;
import com.sigau.certificacion.repository.CertificacionRepository;
import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificacionService {

    private final CertificacionRepository certificacionRepository;
    private final DemandaRepository demandaRepository;
    private final UsuarioRepository usuarioRepository;

    public CertificacionService(
            CertificacionRepository certificacionRepository,
            DemandaRepository demandaRepository,
            UsuarioRepository usuarioRepository) {
        this.certificacionRepository = certificacionRepository;
        this.demandaRepository = demandaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Certificacion> listarTodas() {
        return certificacionRepository.findAll();
    }

    public Certificacion buscarPorId(String id) {
        return certificacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Certificación no encontrada"));
    }

    public Certificacion guardar(Certificacion certificacion) {

        Demanda demanda = demandaRepository
                .findById(certificacion.getDemanda().getId())
                .orElseThrow(() ->
                        new RuntimeException("Demanda no encontrada"));

        Usuario usuario = usuarioRepository
                .findById(certificacion.getUsuario().getId())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        certificacion.setDemanda(demanda);
        certificacion.setUsuario(usuario);

        return certificacionRepository.save(certificacion);
    }
}

