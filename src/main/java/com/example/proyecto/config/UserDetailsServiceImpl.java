package com.example.proyecto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.proyecto.domain.Usuario;
import com.example.proyecto.services.MainService;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final MainService dibujoService;

 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = dibujoService.obtenerUsuario(username);
    if (usuario == null)  
     throw (new UsernameNotFoundException("Usuario no encontrado!"));
   return User      
     .withUsername(username)
     .roles(usuario.getRol().toString())
     .password(usuario.getContrasena())
     .build();  
 } 
}