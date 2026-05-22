package com.example.proyecto.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400ConnectionPool;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.CommandCall;
import com.ibm.as400.access.ErrorCompletingRequestException;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
public class AS400ServicePrueba {

    // Crear objeto para el sistema
    AS400 pub400 = new AS400("PUB400.com");
    /* 
        El hacer el new para cada petición realentiza mucho el funcionamiento y la API, por ello
        es mejor hacer un pool de conexiones
    */
    AS400ConnectionPool pool = new AS400ConnectionPool();
    
    public void conectar(){
        try {
            // Conectar al sistema, opcional, al hacer una operación se conecta automáticamente
            // AS400.DATABASE es para SQL/Archivos.
            // AS400.COMMAND es para llamar programas (CALL) o comandos (CL).
            pub400.connectService(AS400.COMMAND);
        } catch (AS400SecurityException e) {
            throw new RuntimeException("Error al conectar");
        } catch (IOException e) {
            throw new RuntimeException("Error al conectar");
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar");
        }
    }

    public void desconectar(){
        // Desconectar el sistema
        pub400.disconnectService(AS400.COMMAND);
    }

    public void accion(String comando){
        try {
            // Crear comando y ejecutarlo, puede causar 4 errores distintos XD
            CommandCall cmd1 = new CommandCall(pub400, comando);
            cmd1.run();
            /* 
                cada que se ejecute un comando se hará conexión al sistema en caso de no tenerla ya,
                por ello se debe desconectar tras todos los comandos si es secuencial o tras cada uno
                si es bajo llamadas de método
            */
        } catch (AS400SecurityException e) {
            throw new RuntimeException("Error al ejecutar");
        } catch (ErrorCompletingRequestException e) {
            throw new RuntimeException("Error al ejecutar");
        } catch (IOException e) {
            throw new RuntimeException("Error al ejecutar");
        } catch (InterruptedException e) {
            throw new RuntimeException("Error al ejecutar");
        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar");
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // CONSULTAR (Select)
    public List<Map<String, Object>> obtenerDibujos() {
        String sql = "SELECT * FROM IRIAHN1.DIBUJOS";
        // jdbcTemplate maneja todo el ruido de abrir/cerrar conexiones
        return jdbcTemplate.queryForList(sql);
    }

    // DAR DE ALTA (Insert)
    public void crearUsuario(String id, String nombre) {
        String sql = "INSERT INTO BIBLIOTECA.TABLA_USU (ID, NOMBRE) VALUES (?, ?)";
        jdbcTemplate.update(sql, id, nombre);
    }

    // MODIFICAR (Update)
    public void actualizarNombre(String id, String nuevoNombre) {
        String sql = "UPDATE BIBLIOTECA.TABLA_USU SET NOMBRE = ? WHERE ID = ?";
        jdbcTemplate.update(sql, nuevoNombre, id);
    }

    // DAR DE BAJA (Delete)
    public void eliminarUsuario(String id) {
        String sql = "DELETE FROM BIBLIOTECA.TABLA_USU WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }

}

// Fuentes:
// https://www.ibm.com/docs/es/i/7.5.0?topic=programming-managing-connections-in-java-programs