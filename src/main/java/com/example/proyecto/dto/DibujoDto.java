package com.example.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class DibujoDto {

    private Long id;

    private String titulo;

    private String tematica;
    
    private int precio;

    private String imagen;

    private Double puntuacion;
}
