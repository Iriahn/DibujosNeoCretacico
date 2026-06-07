package com.example.proyecto.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.proyecto.services.AS400ServicePrueba;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "AS400Controller", description = "Controlador para los endpoint de la API de BBDD del AS400")
@RestController
@RequiredArgsConstructor
public class AS400Controller {

    private final AS400ServicePrueba as400ServicePrueba;
    
    // 1. OBTENER TODOS LOS DIBUJOS (GET)
    @GetMapping("/api400/dibujos")
    public ResponseEntity<List<Map<String, Object>>> listar() {
        List<Map<String, Object>> dibujos = as400ServicePrueba.obtenerDibujos();
        return ResponseEntity.ok(dibujos);
    }

    // 2. CREAR UN USUARIO (POST)
    // Se envía un JSON: {"id": "100", "nombre": "Carlos"}
    @PostMapping("/api400/usuarios")
    public ResponseEntity<String> crear(@RequestBody Map<String, String> body) {
        try {
            as400ServicePrueba.crearUsuario(body.get("id"), body.get("nombre"));
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con éxito en AS400");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // 3. ACTUALIZAR UN USUARIO (PUT)
    @PutMapping("/api400/usuarios/{id}")
    public ResponseEntity<String> actualizar(@PathVariable String id, @RequestBody Map<String, String> body) {
        as400ServicePrueba.actualizarNombre(id, body.get("nombre"));
        return ResponseEntity.ok("Usuario actualizado");
    }

    // 4. ELIMINAR UN USUARIO (DELETE)
    @DeleteMapping("/api400/usuarios/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        as400ServicePrueba.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado");
    }

}