
package com.sigau.reporte.repository;

import com.sigau.demanda.Demanda;
import com.sigau.intervencion.Intervencion;
import com.sigau.inspeccion.Inspeccion;
import com.sigau.ejemplar.Ejemplar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReporteRepository extends JpaRepository<Demanda, Integer> {

    // Demandas
    @Query("SELECT d.estado, COUNT(d) FROM Demanda d GROUP BY d.estado")
    List<Object[]> contarDemandasPorEstado();

    @Query("SELECT d.origen.nombre, COUNT(d) FROM Demanda d GROUP BY d.origen.nombre")
    List<Object[]> contarDemandasPorOrigen();

    // Intervenciones
    @Query("SELECT i.tipoIntervencion.nombre, COUNT(i) FROM Intervencion i GROUP BY i.tipoIntervencion.nombre")
    List<Object[]> contarIntervencionesPorTipo();

    @Query("SELECT i.estado, COUNT(i) FROM Intervencion i GROUP BY i.estado")
    List<Object[]> contarIntervencionesPorEstado();

    // Inspecciones
    @Query("SELECT COUNT(i) FROM Inspeccion i")
    Long contarInspecciones();

    // Ejemplares
    @Query("SELECT e.estado, COUNT(e) FROM Ejemplar e GROUP BY e.estado")
    List<Object[]> contarEjemplaresPorEstado();

    @Query("SELECT COUNT(i) FROM Intervencion i")
    Long contarIntervenciones();

    @Query("SELECT COUNT(e) FROM Ejemplar e")
    Long contarEjemplares();
}

