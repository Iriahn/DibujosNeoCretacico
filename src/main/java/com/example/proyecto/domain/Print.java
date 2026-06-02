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
public class Print {

    private BigDecimal id;

    @NotNull
    private String nombre;
    
    @NotNull
    private String redContacto;

    @NotNull
    private String redNombre;
    
    @NotNull
    private BigDecimal print;

    @NotNull
    private Integer unidades;

    @NotNull
    private String tamano;

    @NotNull
    private String tipo;

    private String comentarios;

    private BigDecimal precio;

    private EstadoPrintEnum estado;
}
