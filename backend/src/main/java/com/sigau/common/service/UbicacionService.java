package com.sigau.common.service;

import com.sigau.common.Textos;
import com.sigau.common.Ubicacion;
import com.sigau.common.dto.UbicacionRequest;
import com.sigau.common.exception.RecursoNoEncontradoException;
import com.sigau.common.repository.UbicacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    public UbicacionService(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<Ubicacion> listarTodas() {
        return ubicacionRepository.findAll();
    }

    public Ubicacion buscarPorId(Integer id) {
        return ubicacionRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Ubicación no encontrada"));
    }

    public Ubicacion guardar(UbicacionRequest request) {

        String calle = Textos.limpiar(request.getCalle());
        String numero = Textos.limpiar(request.getNumero());
        String barrio = Textos.limpiar(request.getBarrio());

        return ubicacionRepository
                .buscarIguales(
                        calle,
                        numero == null ? "" : numero,
                        barrio == null ? "" : barrio)
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    Ubicacion ubicacion = new Ubicacion();
                    ubicacion.setCalle(calle);
                    ubicacion.setNumero(numero);
                    ubicacion.setBarrio(barrio);
                    ubicacion.setComuna(Textos.limpiar(request.getComuna()));
                    ubicacion.setReferencia(Textos.limpiar(request.getReferencia()));
                    return ubicacionRepository.save(ubicacion);
                });
    }
}
