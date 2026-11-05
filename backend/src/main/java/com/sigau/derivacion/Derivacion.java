
package com.sigau.derivacion;

import com.sigau.demanda.Demanda;
import com.sigau.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "derivacion")
public class Derivacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "demanda_id")
    private Demanda demanda;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "sistema_destino", length = 50)
    private String sistemaDestino;

    private LocalDate fecha;

    @Column(length = 50)
    private String estado;

    @Column(name = "referencia_externa", length = 100)
    private String referenciaExterna;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    public Derivacion() {
    }

    // Getters y setters de todos los campos

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Demanda getDemanda() {
        return demanda;
    }

    public void setDemanda(Demanda demanda) {
        this.demanda = demanda;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSistemaDestino() {
        return sistemaDestino;
    }

    public void setSistemaDestino(String sistemaDestino) {
        this.sistemaDestino = sistemaDestino;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReferenciaExterna() {
        return referenciaExterna;
    }

    public void setReferenciaExterna(String referenciaExterna) {
        this.referenciaExterna = referenciaExterna;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

