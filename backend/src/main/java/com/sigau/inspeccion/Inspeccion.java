package com.sigau.inspeccion;

import com.sigau.ejemplar.Ejemplar;
import com.sigau.usuario.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inspeccion")
public class Inspeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ejemplar_id")
    private Ejemplar ejemplar;

    @ManyToOne
    @JoinColumn(name = "inspector_id")
    private Usuario inspector;

    private LocalDate fecha;

    @Column(name = "especie_identificada", length = 100)
    private String especieIdentificada;

    @Column(precision = 5, scale = 2)
    private BigDecimal altura;

    @Column(precision = 6, scale = 2)
    private BigDecimal dap;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    private String estado;

    public Inspeccion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public Usuario getInspector() {
        return inspector;
    }

    public void setInspector(Usuario inspector) {
        this.inspector = inspector;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEspecieIdentificada() {
        return especieIdentificada;
    }

    public void setEspecieIdentificada(String especieIdentificada) {
        this.especieIdentificada = especieIdentificada;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public BigDecimal getDap() {
        return dap;
    }

    public void setDap(BigDecimal dap) {
        this.dap = dap;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
