package com.sigau.demanda;

import jakarta.persistence.*;

@Entity
@Table(name = "origen_demanda")
public class OrigenDemanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    public OrigenDemanda() {


    }
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
