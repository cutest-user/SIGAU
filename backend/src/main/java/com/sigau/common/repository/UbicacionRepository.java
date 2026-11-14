package com.sigau.common.repository;

import com.sigau.common.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {

    @Query("""
            select u from Ubicacion u
            where lower(u.calle) = lower(:calle)
              and coalesce(lower(u.numero), '') = lower(:numero)
              and coalesce(lower(u.barrio), '') = lower(:barrio)
            order by u.id
            """)
    List<Ubicacion> buscarIguales(
            @Param("calle") String calle,
            @Param("numero") String numero,
            @Param("barrio") String barrio);
}
