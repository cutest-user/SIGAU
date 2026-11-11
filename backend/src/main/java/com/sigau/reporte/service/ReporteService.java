
package com.sigau.reporte.service;

import com.sigau.reporte.repository.ReporteRepository;
import com.sigau.reporte.dto.ReporteResponseDTO;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public ReporteResponseDTO generarReporte() {

        ReporteResponseDTO reporte = new ReporteResponseDTO();

        // Demandas
        reporte.setTotalDemandas(reporteRepository.count());
        reporte.setDemandasPorEstado(
                convertirResultados(reporteRepository.contarDemandasPorEstado())
        );
        reporte.setDemandasPorOrigen(
                convertirResultados(reporteRepository.contarDemandasPorOrigen())
        );

        // Intervenciones
        reporte.setTotalIntervenciones(reporteRepository.contarIntervenciones());
        reporte.setIntervencionesPorTipo(
                convertirResultados(reporteRepository.contarIntervencionesPorTipo())
        );
        reporte.setIntervencionesPorEstado(
                convertirResultados(reporteRepository.contarIntervencionesPorEstado())
        );

        // Inspecciones
        reporte.setTotalInspecciones(reporteRepository.contarInspecciones());

        // Ejemplares
        reporte.setTotalEjemplares(reporteRepository.contarEjemplares());
        reporte.setEjemplaresPorEstado(
                convertirResultados(reporteRepository.contarEjemplaresPorEstado())
        );

        return reporte;
    }

    private Map<String, Long> convertirResultados(List<Object[]> resultados) {

        Map<String, Long> mapa = new LinkedHashMap<>();

        for (Object[] fila : resultados) {

            String clave = fila[0] != null ? fila[0].toString() : "SIN DATO";
            Long cantidad = ((Number) fila[1]).longValue();

            mapa.put(clave, cantidad);
        }

        return mapa;
    }
}

