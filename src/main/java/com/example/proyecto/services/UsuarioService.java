package com.example.proyecto.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.proyecto.domain.Usuario;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;


    // public List<Usuario> obtenerUsuarios(){
    //     return usuarioRepository.findAll();
    // }

    // public Usuario obtenerUsuario(Long id) throws RuntimeException{
    //     return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));  
    // }

    // public boolean anadirUsu(Usuario usuario) {
    //     String passCrypted = passwordEncoder.encode(usuario.getContrasena());
    //     usuario.setContrasena(passCrypted);
    //     try {
    //         if(usuarioRepository.findByNombre(usuario.getNombre()) != null)
    //             throw new RuntimeException("Nombre ya en uso");

    //         usuarioRepository.save(usuario);
    //         return true;
    //     } catch (DataIntegrityViolationException e) {
    //         throw new RuntimeException("Error al añadir usuario");

    //     }
    // }

    // public Usuario editar(Usuario usuario) {
    //     String passCrypted = passwordEncoder.encode(usuario.getContrasena());
    //     usuario.setContrasena(passCrypted);
    //     try {
    //         Usuario usercopy = usuarioRepository.findByNombre(usuario.getNombre());
    //         if(usercopy != null && usercopy.getId() != usuario.getId())
    //             throw new RuntimeException("Nombre ya en uso");

    //         return usuarioRepository.save(usuario);
    //     } catch (DataIntegrityViolationException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // public void borrarUsu(Long id) throws RuntimeException{
    //     obtenerUsuario(id);
    //     usuarioRepository.deleteById(id);
    // }
    
    // public Usuario obtenerUsuarioConectado() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (!(authentication instanceof AnonymousAuthenticationToken)) {
    //         String nombreUsuarioContectado = authentication.getName();
    //         return usuarioRepository.findByNombre(nombreUsuarioContectado);
    //     }
    //     return null;
    // }
}
