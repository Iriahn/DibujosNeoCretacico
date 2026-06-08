package com.example.proyecto.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.proyecto.services.AS400Service;

import lombok.RequiredArgsConstructor;

@Tag(name = "AS400Controller", description = "Controlador para los endpoint de la API de BBDD del AS400")
@RestController
@RequiredArgsConstructor
public class AS400Controller {

    private final AS400Service as400Service;
    
    // 1. OBTENER TODOS LOS DIBUJOS (GET)
    @GetMapping("/api400/dibujos")
    public ResponseEntity<List<Map<String, Object>>> listarDibs() {
        List<Map<String, Object>> dibujos = as400Service.obtenerDibujos();
        return ResponseEntity.ok(dibujos);
    }

    @GetMapping("/api400/usuarios")
    public ResponseEntity<List<Map<String, Object>>> listarUsers() {
        List<Map<String, Object>> usuarios = as400Service.obtenerUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    
    @GetMapping("/api400/prints")
    public ResponseEntity<List<Map<String, Object>>> listarPrints() {
        List<Map<String, Object>> prints = as400Service.obtenerPrints();
        return ResponseEntity.ok(prints);
    }

}