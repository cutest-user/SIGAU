package com.sigau.demanda;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "demanda")
public class Demanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "origen_id")
    private Integer origenId;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "ubicacion_id")
    private Integer ubicacionId;

    private LocalDateTime fecha;

    private String motivo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 50)
    private String estado;

    @Column(length = 20)
    private String prioridad;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    public Demanda() {
    }
}
