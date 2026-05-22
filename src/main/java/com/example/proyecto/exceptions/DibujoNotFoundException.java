package com.example.proyecto.exceptions;

public class DibujoNotFoundException extends RuntimeException{
    public DibujoNotFoundException(){
        super("Dibujo no encontrado");
    }
}
