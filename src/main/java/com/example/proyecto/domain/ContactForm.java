package com.example.proyecto.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {

    @NotNull
    private String nombre;

    @NotNull
    private String dni;

    @NotNull
    @Email
    private String email;
    
    @NotNull
    private String direccion;

    @NotNull
    private Integer opcion;

    @NotNull
    private boolean terminos;
    
}
