package com.example.proyecto.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.proyecto.domain.RolEnum;
import com.example.proyecto.domain.Usuario;
import com.example.proyecto.services.AS400Service;
import com.example.proyecto.services.MainService;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AS400Service as400Service;

 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
  Map<String, Object> datosuser = as400Service.obtenerUsuarioNombre(username);
  if (datosuser == null)  
    throw (new UsernameNotFoundException("Usuario no encontrado!"));
  
  Usuario usuario = new Usuario(null, datosuser.get("NOMBRE").toString(), datosuser.get("REDPREFERIDA").toString(), datosuser.get("USERPREFERIDA").toString(), datosuser.get("CONTRASENA").toString(), RolEnum.valueOf(datosuser.get("ROL").toString()));
  return User      
    .withUsername(username)
    .roles(usuario.getRol().toString())
    .password(usuario.getContrasena())
    .build();  
 } 
}