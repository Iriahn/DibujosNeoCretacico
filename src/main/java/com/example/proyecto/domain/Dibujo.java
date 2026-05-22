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
public class Dibujo {

    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String tematica;
    
    @NotNull
    private String descripcion;
    
    @NotNull
    private String finalidad;

    @NotNull
    @ManyToOne
    private EstiloEnum estilo;

    @NotNull
    @ManyToOne
    private CategoriaEnum categoria;

    @NotNull
    private int anho;

    @NotNull
    private BigDecimal precio;

    private String imagen;

    private Boolean completado;

}
