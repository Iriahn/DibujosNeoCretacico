package com.example.proyecto.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto.domain.Dibujo;
import com.example.proyecto.domain.Pedido;
import com.example.proyecto.domain.RolEnum;
import com.example.proyecto.domain.Usuario;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
public class AS400Service {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    // -------------------------- Dibujos ------------------------------------------
    public List<Map<String, Object>> obtenerDibujos() {
        String sql = "SELECT * FROM IRIAHN1.DIBUJOS";
        // jdbcTemplate maneja todo el ruido de abrir/cerrar conexiones
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obtenerDibujo(Long id) {
        String sql = "SELECT * FROM IRIAHN1.DIBUJOS D WHERE D.IDDIB = ?";
        return jdbcTemplate.queryForList(sql, id);
    }
    
    public void crearDibujo(Dibujo dibujo) {
        // El id no es necesario porque es autogenerado
        String sql = "INSERT INTO IRIAHN1.DIBUJOS (TITULO, TEMATICA, DESCRIPCION, FINALIDAD, ESTILO, CATEGORIA, ANHO, PRECIO, IMAGEN, COMPLETADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, dibujo.getTitulo(), dibujo.getTematica(), dibujo.getDescripcion(), dibujo.getFinalidad(), dibujo.getEstilo(), dibujo.getCategoria(), dibujo.getAnho(), dibujo.getPrecio(), dibujo.getImagen(), dibujo.getCompletado());
    }
    
    @Transactional
    public BigDecimal altaUltId(Dibujo dibujo) {
        // Recupero el último ID para asignarlo al pedido correspondiente
        crearDibujo(dibujo);
        String sql = "SELECT identity_val_local() FROM SYSIBM.SYSDUMMY1";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class);
    }

    public void actualizarDibujo(Dibujo dibujo) {
        String sql = "UPDATE IRIAHN1.DIBUJOS SET TITULO = ?, TEMATICA = ?, DESCRIPCION = ?, FINALIDAD = ?, ESTILO = ?, CATEGORIA = ?, ANHO = ?, PRECIO = ?, IMAGEN = ?, COMPLETADO = ? WHERE ID = ?";
        jdbcTemplate.update(sql, dibujo.getTitulo(), dibujo.getTematica(), dibujo.getDescripcion(), dibujo.getFinalidad(), dibujo.getEstilo(), dibujo.getCategoria(), dibujo.getAnho(), dibujo.getPrecio(), dibujo.getImagen(), dibujo.getCompletado(), dibujo.getId());
    }

    public void eliminarDibujo(Long id) {
        String sql = "DELETE FROM IRIAHN1.DIBUJOS WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }

    // -------------------------- Usuarios -----------------------------------------
    public List<Map<String, Object>> obtenerUsuarios() {
        String sql = "SELECT * FROM IRIAHN1.USUARIOS";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obtenerUsuario(Long id) {
        String sql = "SELECT * FROM IRIAHN1.USUARIOS D WHERE D.IDDIB = ?";
        return jdbcTemplate.queryForList(sql, id);
    }

    public void crearUsuario(Usuario usuario) {
        String contrasena = passwordEncoder.encode(usuario.getContrasena());
        String sql = "INSERT INTO IRIAHN1.USUARIOS (NOMBRE, REDPREFERIDA, USERPREFERIDA, CONTRASENA, ROL) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, usuario.getNombre(), usuario.getRedcontacto(), usuario.getUsercontacto(), contrasena, usuario.getRol().toString());
    }

    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE IRIAHN1.USUARIOS SET NOMBRE = ?, REDPREFERIDA = ?, USERPREFERIDA = ?, ROL = ? WHERE ID = ?";
        jdbcTemplate.update(sql, usuario.getNombre(), usuario.getRedcontacto(), usuario.getUsercontacto(), usuario.getRol().toString(), usuario.getId());
    }

    public void eliminarUsuario(Long id) {
        String sql = "DELETE FROM IRIAHN1.USUARIOS WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
    
    // -------------------------- Pedidos ------------------------------------------
    public List<Map<String, Object>> obtenerPedidos() {
        String sql = "SELECT * FROM IRIAHN1.PEDIDOS";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obtenerPedido(Long id) {
        String sql = "SELECT * FROM IRIAHN1.PEDIDOS D WHERE D.IDDIB = ?";
        return jdbcTemplate.queryForList(sql, id);
    }

    public void crearPedido(Pedido pedido) {
        /* Inserto el dibujo, estado pendiente p.e., recupero el id que se le ha asignado
            y ese id de dibujo es el que se le asigna al pedido
            Controller:
            - Recibo pedido, inserto dibujo, asigno id al pedido, mando aquí el pedido */
        BigDecimal dibid = altaUltId(new Dibujo(null, null, null, null, pedido.getFinalidad(), pedido.getEstilo(), pedido.getCategoria(), LocalDate.now().getYear(), pedido.getPrecio(), null, false));
        String sql = "INSERT INTO IRIAHN1.PEDIDOS (REDCONTACTO, USERCONTACTO, ESTADO, PRECIO, FINALIDAD, ESTILO, CATEGORIA, IDDIB, IDUSU) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, pedido.getRedContacto(), pedido.getRedNombre(), pedido.getEstado(), pedido.getPrecio(), pedido.getFinalidad(), pedido.getEstilo(), pedido.getCategoria(), dibid, pedido.getUsuario());
    }

    public void actualizarPedido(Pedido pedido) {//-----------------------------------
        String sql = "UPDATE IRIAHN1.PEDIDOS SET REDCONTACTO = ?, USERCONTACTO = ?, ESTADO = ?, PRECIO = ?, FINALIDAD = ?, ESTILO = ?, CATEGORIA = ?, IDDIB = ?, IDUSU = ? WHERE ID = ?";
        jdbcTemplate.update(sql, pedido.getRedContacto(), pedido.getRedNombre(), pedido.getEstado().toString(), pedido.getPrecio(), pedido.getFinalidad(), pedido.getEstilo(), pedido.getCategoria(), pedido.getDibujo().getId(), pedido.getUsuario().getId(), pedido.getId());
    }

}

// Fuentes:
// https://www.ibm.com/docs/es/i/7.5.0?topic=programming-managing-connections-in-java-programs

// Render para hostear Back, Vercel para Front

// Select identity_val_local() into :CabIDRRN from SYSIBM.SYSDUMMY1