package com.sigau.common;

import jakarta.persistence.*;

@Entity
@Table(name = "ubicacion")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 150)
    private String calle;

    @Column(length = 20)
    private String numero;

    @Column(length = 100)
    private String barrio;

    @Column(length = 50)
    private String comuna;

    @Column(length = 255)
    private String referencia;

    public Ubicacion() {
    }
    public Integer getId() {
        return id;
    }
}
