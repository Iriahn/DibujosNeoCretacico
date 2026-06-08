package com.example.proyecto.services;

import java.time.LocalDate;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.proyecto.domain.CategoriaEnum;
import com.example.proyecto.domain.RolEnum;
import com.example.proyecto.domain.SubCategoriaEnum;
import com.example.proyecto.domain.Usuario;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
public class MainService {

    private final AS400Service as400Service;
    
    public CategoriaEnum[] obtenerCategorias(){
        return CategoriaEnum.values();
    }

    public SubCategoriaEnum[] obtenerSubCategorias(){
        return SubCategoriaEnum.values();
    }

    public Usuario obtenerUsuarioConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String nombreUsuarioConectado = authentication.getName();
            Map<String, Object> user = as400Service.obtenerUsuarioNombre(nombreUsuarioConectado);
            if(user == null)
                return null;
            Usuario usuario = new Usuario(null, user.get("NOMBRE").toString(), user.get("REDPREFERIDA").toString(), user.get("USERPREFERIDA").toString(), user.get("CONTRASENA").toString(), RolEnum.valueOf(user.get("ROL").toString()));
            return usuario;
        }
        return null;
    }

}
