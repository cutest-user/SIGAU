package com.sigau.usuario;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nombre;

    @Column(length = 150)
    private String apellido;

    @Column(length = 150)
    private String email;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(name = "rol_id")
    private Integer rolId;

    private Boolean activo;

    public Usuario() {
    }
    public Integer getId() {
        return id;
    }
}
