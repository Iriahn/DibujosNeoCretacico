package com.example.proyecto.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto.domain.Dibujo;
import com.example.proyecto.domain.Pedido;
import com.example.proyecto.domain.Print;
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

    public Map<String, Object> obtenerDibujo(Long id) {
        String sql = "SELECT * FROM IRIAHN1.DIBUJOS D WHERE D.IDDIB = ?";
        return jdbcTemplate.queryForList(sql, id).stream().findFirst().orElse(null);
    }
    
    public Map<String, Object> obtenerDibujoTitulo(String titulo) {
        String sql = "SELECT * FROM IRIAHN1.DIBUJOS D WHERE D.TITULO = ?";
        return jdbcTemplate.queryForList(sql, titulo).stream().findFirst().orElse(null);
    }
    
    public void crearDibujo(Dibujo dibujo) {
        // El id no es necesario porque es autogenerado
        String sql = "INSERT INTO IRIAHN1.DIBUJOS (TITULO, TEMATICA, DESCRIPCION, FINALIDAD, CATEGORIA, SUBCATEGORIA, ANHO, PRECIO, IMAGEN, COMPLETADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, dibujo.getTitulo(), dibujo.getTematica(), dibujo.getDescripcion(), dibujo.getFinalidad(), dibujo.getCategoria().toString(), dibujo.getSubcategoria().toString(), dibujo.getAnho(), dibujo.getPrecio(), dibujo.getImagen(), dibujo.getCompletado());
    }
    
    @Transactional
    public BigDecimal altaUltId(Dibujo dibujo) {
        // Recupero el último ID para asignarlo al pedido correspondiente
        crearDibujo(dibujo);
        String sql = "SELECT identity_val_local() FROM SYSIBM.SYSDUMMY1";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class);
    }

    public void actualizarDibujo(Dibujo dibujo) {
        String sql = "UPDATE IRIAHN1.DIBUJOS SET TITULO = ?, TEMATICA = ?, DESCRIPCION = ?, FINALIDAD = ?, CATEGORIA = ?, SUBCATEGORIA = ?, ANHO = ?, PRECIO = ?, IMAGEN = ?, COMPLETADO = ? WHERE IDDIB = ?";
        jdbcTemplate.update(sql, dibujo.getTitulo(), dibujo.getTematica(), dibujo.getDescripcion(), dibujo.getFinalidad(), dibujo.getCategoria().toString(), dibujo.getSubcategoria().toString(), dibujo.getAnho(), dibujo.getPrecio(), dibujo.getImagen(), dibujo.getCompletado(), dibujo.getId());
    }

    public void eliminarDibujo(Long id) {
        String sql = "DELETE FROM IRIAHN1.DIBUJOS WHERE IDDIB = ?";
        jdbcTemplate.update(sql, id);
    }

    // -------------------------- Usuarios -----------------------------------------
    public List<Map<String, Object>> obtenerUsuarios() {
        String sql = "SELECT * FROM IRIAHN1.USUARIOS";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obtenerUsuario(Long id) {
        String sql = "SELECT * FROM IRIAHN1.USUARIOS U WHERE U.IDUSU = ?";
        return jdbcTemplate.queryForList(sql, id);
    }
    
    public Map<String, Object> obtenerUsuarioNombre(String nombre) {
        String sql = "SELECT * FROM IRIAHN1.USUARIOS U WHERE U.NOMBRE = ?";
        return jdbcTemplate.queryForList(sql, nombre).stream().findFirst().orElse(null);
    }

    public void crearUsuario(Usuario usuario) {
        String contrasena = passwordEncoder.encode(usuario.getContrasena());
        String sql = "INSERT INTO IRIAHN1.USUARIOS (NOMBRE, REDPREFERIDA, USERPREFERIDA, CONTRASENA, ROL) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, usuario.getNombre(), usuario.getRedcontacto(), usuario.getUsercontacto(), contrasena, usuario.getRol().toString());
    }
    
    public void cambiarContrasena(Usuario usuario) {
        String contrasena = passwordEncoder.encode(usuario.getContrasena());
        String sql = "UPDATE IRIAHN1.USUARIOS SET CONTRASENA = ? WHERE IDUSU = ?";
        jdbcTemplate.update(sql, contrasena, usuario.getId());
    }

    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE IRIAHN1.USUARIOS SET NOMBRE = ?, REDPREFERIDA = ?, USERPREFERIDA = ?, ROL = ? WHERE IDUSU = ?";
        jdbcTemplate.update(sql, usuario.getNombre(), usuario.getRedcontacto(), usuario.getUsercontacto(), usuario.getRol().toString(), usuario.getId());
    }

    public void eliminarUsuario(Long id) {
        String sql = "DELETE FROM IRIAHN1.USUARIOS WHERE IDUSU = ?";
        jdbcTemplate.update(sql, id);
    }
    
    // -------------------------- Prints -------------------------------------------
    public List<Map<String, Object>> obtenerPrints() {
        String sql = "SELECT * FROM IRIAHN1.PRINTS";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> obtenerPrint(Long id) {
        String sql = "SELECT * FROM IRIAHN1.PRINTS PR WHERE PR.IDPRI = ?";
        return jdbcTemplate.queryForList(sql, id).stream().findFirst().orElse(null);
    }

    public void crearPrint(Print print) {
        String sql = "INSERT INTO IRIAHN1.PRINTS (NOMBRE, REDPREFERIDA, USERPREFERIDA, UNIDADES, TAMANO, TIPO, COMENTARIOS, PRECIO, ESTADO, IDDIB) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'EN PREPARACION', ?)";
        jdbcTemplate.update(sql, print.getNombre(), print.getRedContacto(), print.getRedNombre(), print.getUnidades(), print.getTamano(), print.getTipo(), print.getComentarios(), print.getPrecio(), print.getPrint());
    }

    public void actualizarPrint(Print print) {//-----------------------------------
        String sql = "UPDATE IRIAHN1.PRINTS SET NOMBRE = ?, REDPREFERIDA = ?, USERPREFERIDA = ?, UNIDADES = ?, TAMANO = ?, TIPO = ?, COMENTARIOS = ?, PRECIO = ?, ESTADO = ?, IDDIB = ? WHERE IDPRI = ?";
        jdbcTemplate.update(sql,  print.getNombre(), print.getRedContacto(), print.getRedNombre(), print.getUnidades(), print.getTamano(), print.getTipo(), print.getComentarios(), print.getPrecio(), print.getEstado().toString(), print.getPrint(), print.getId());
    }

    public void eliminarPrint(Long id) {
        String sql = "DELETE FROM IRIAHN1.PRINTS WHERE IDPRI = ?";
        jdbcTemplate.update(sql, id);
    }

    // -------------------------- Pedidos ------------------------------------------
    public List<Map<String, Object>> obtenerPedidos() {
        String sql = "SELECT * FROM IRIAHN1.PEDIDOS";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obtenerPedido(Long id) {
        String sql = "SELECT * FROM IRIAHN1.PEDIDOS D WHERE D.IDPED = ?";
        return jdbcTemplate.queryForList(sql, id);
    }

    public void crearPedido(Pedido pedido) {
        /* Inserto el dibujo, estado pendiente p.e., recupero el id que se le ha asignado
            y ese id de dibujo es el que se le asigna al pedido
            Controller:
            - Recibo pedido, inserto dibujo, asigno id al pedido, mando aquí el pedido */
        BigDecimal dibid = altaUltId(new Dibujo(null, null, null, null, pedido.getFinalidad(), pedido.getCategoria(), pedido.getSubcategoria(), LocalDate.now().getYear(), pedido.getPrecio(), null, false));
        String sql = "INSERT INTO IRIAHN1.PEDIDOS (REDCONTACTO, USERCONTACTO, SUBCATEGORIA, PRECIO, FINALIDAD, ESTILO, CATEGORIA, IDDIB, IDUSU) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, pedido.getRedContacto(), pedido.getRedNombre(), pedido.getEstado(), pedido.getPrecio(), pedido.getFinalidad(), pedido.getSubcategoria(), pedido.getCategoria(), dibid, pedido.getUsuario());
    }

    public void actualizarPedido(Pedido pedido) {//-----------------------------------
        String sql = "UPDATE IRIAHN1.PEDIDOS SET REDCONTACTO = ?, USERCONTACTO = ?, SUBCATEGORIA = ?, PRECIO = ?, FINALIDAD = ?, ESTILO = ?, CATEGORIA = ?, IDDIB = ?, IDUSU = ? WHERE IDPED = ?";
        jdbcTemplate.update(sql, pedido.getRedContacto(), pedido.getRedNombre(), pedido.getEstado().toString(), pedido.getPrecio(), pedido.getFinalidad(), pedido.getSubcategoria(), pedido.getCategoria(), pedido.getDibujo().getId(), pedido.getUsuario().getId(), pedido.getId());
    }

}

// Fuentes:
// https://www.ibm.com/docs/es/i/7.5.0?topic=programming-managing-connections-in-java-programs

// Render para hostear Back, Vercel para Front

// Select identity_val_local() into :CabIDRRN from SYSIBM.SYSDUMMY1