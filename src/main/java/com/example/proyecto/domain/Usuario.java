package com.example.proyecto.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Usuario {

    private BigDecimal id;

    private String nombre;

    private String redcontacto;
    
    private String usercontacto;

    private String contrasena;

    private RolEnum rol;
    
}
