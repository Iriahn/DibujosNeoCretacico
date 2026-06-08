package com.example.proyecto.controllers;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.proyecto.domain.RolEnum;
import com.example.proyecto.domain.Usuario;
import com.example.proyecto.services.AS400Service;
import com.example.proyecto.services.MainService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequiredArgsConstructor
public class UserController {

    private final AS400Service as400Service;
    private final MainService mainService;
    private String txterror;
    
    @GetMapping("/usuario/userlist")
    public String showUsers(Model model) {
        if(txterror != null)
            model.addAttribute("error", txterror);
        txterror = null;
        return "crudusuario/userListView";
    }

    @GetMapping("/usuario/newUser")
    public String nuevousuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "crudusuario/newUserView";
    }

    @PostMapping("/usuario/newUser/submit")
    public String newUser(@Valid Usuario usuario, BindingResult bindingresult) {
        try{
            if(bindingresult.hasErrors())
                txterror = "Error en el procesado";
            else
                as400Service.crearUsuario(usuario);
        }catch(RuntimeException ex){
            txterror = ex.getMessage();
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/usuario/userlist";
    }

    @GetMapping("/usuario/register")
    public String showAutoNew(Model model) {
        model.addAttribute("usuarioForm", new Usuario());
        return "crudusuario/autoUserNewView";
    }

    @PostMapping("/usuario/register/submit")
    public String showAutoSubmit(@Valid @ModelAttribute("usuarioForm") Usuario nuevoUsuario, BindingResult bindingresult) {
        try{
            nuevoUsuario.setRol(RolEnum.USER);
            if(bindingresult.hasErrors())
                txterror = "Error en el procesado";
            else
                as400Service.crearUsuario(nuevoUsuario);
        }catch(RuntimeException ex){
            txterror = ex.getMessage();
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/usuario/userlist";
    }

    @GetMapping("/usuario/changepassword")
    public String showChangeForm(Model model) {
        Usuario usuario = mainService.obtenerUsuarioConectado();
        if (usuario != null) {
            Map<String, Object> userbbdd = as400Service.obtenerUsuarioNombre(usuario.getNombre());
            Usuario user = new Usuario(new BigDecimal(Long.parseLong(userbbdd.get("IDUSU").toString())), userbbdd.get("NOMBRE").toString(), userbbdd.get("REDPREFERIDA").toString(), userbbdd.get("USERPREFERIDA").toString(), userbbdd.get("CONTRASENA").toString(), RolEnum.valueOf(userbbdd.get("ROL").toString()));
            model.addAttribute("usuarioForm", user);
            return "crudusuario/userChangePasswordView";
        } else {
            return "redirect:/usuario/userlist";
        }
    }

    @PostMapping("/usuario/changepassword/submit")
    public String showChangeSubmit(@Valid @ModelAttribute("usuarioForm") Usuario usuario,
            BindingResult bindingResult) {
        if (!bindingResult.hasErrors()){
            as400Service.cambiarContrasena(usuario);
        }
        return "redirect:/";
    }

    @GetMapping("/usuario/borrar/{id}")
    public String showDeleteUser(@PathVariable Long id) {
        try{
            as400Service.eliminarUsuario(id);
            txterror = "Operación realizada con éxito";
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/usuario/userlist";
    }
    
}