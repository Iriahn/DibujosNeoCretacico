package com.example.proyecto.domain;

import java.math.BigDecimal;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Pedido {

    private Long id;

    @NotNull
    private String redContacto;

    @NotNull
    private String redNombre;
    
    @NotNull
    private EstadoEnum estado;
    
    @NotNull
    private BigDecimal precio;

    @NotNull
    @ManyToOne
    private EstiloEnum estilo;

    @NotNull
    @ManyToOne
    private CategoriaEnum categoria;

    @NotNull
    private String finalidad;

    private Dibujo dibujo;

    private Usuario usuario;

    public Pedido(String redcont, String rednom, EstadoEnum estado, EstiloEnum estilo, CategoriaEnum categoria, String finalidad, Dibujo dibujo, Usuario usuario){
        this.redContacto = redcont;
        this.redNombre = rednom;
        this.estado = estado;
        this.precio = new BigDecimal(0);
        this.estilo = estilo;
        this.categoria = categoria;
        this.finalidad = finalidad;
        this.dibujo = dibujo;
        this.usuario = usuario;
    }

}
