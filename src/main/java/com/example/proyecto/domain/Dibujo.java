package com.example.proyecto.domain;

import java.math.BigDecimal;

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

    private BigDecimal id;

    @NotNull
    private String titulo;

    @NotNull
    private String tematica;
    
    @NotNull
    private String descripcion;
    
    @NotNull
    private String finalidad;


    @NotNull
    private CategoriaEnum categoria;
    
    @NotNull
    private SubCategoriaEnum subcategoria;
    
    @NotNull
    private int anho;

    @NotNull
    private BigDecimal precio;

    private String imagen;

    private Boolean completado;

}
