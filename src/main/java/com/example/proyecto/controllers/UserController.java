package com.example.proyecto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.proyecto.domain.RolEnum;
import com.example.proyecto.domain.Usuario;
import com.example.proyecto.services.UsuarioService;
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

    private final UsuarioService usuarioService;
    private final MainService dibujoService;
    private String txterror;
    
    @GetMapping("/usuario/userlist")
    public String showUsers(Model model) {
        // model.addAttribute("listaususarios", usuarioService.obtenerUsuarios());
        if(txterror != null)
            model.addAttribute("error", txterror);
        txterror = null;
        return "crudusuario/userListView";
    }

    @GetMapping("/usuario/newUser")
    public String nuevousuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("anho", dibujoService.anho());
        return "crudusuario/newUserView";
    }

    @PostMapping("/usuario/newUser/submit")
    public String newUser(@Valid Usuario usuario, BindingResult bindingresult) {
        try{
            if(bindingresult.hasErrors())
                txterror = "Error en el procesado";
            // else
            //     usuarioService.anadirUsu(usuario);
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

    @PostMapping("/usuario/autoUser/submit")
    public String showAutoSubmit(@Valid @ModelAttribute("usuarioForm") Usuario nuevoUsuario, BindingResult bindingResult) {
        
        nuevoUsuario.setRol(RolEnum.USER);

        // if (!bindingResult.hasErrors())
        //     usuarioService.anadirUsu(nuevoUsuario);
        return "redirect:/";
    }

    
    @GetMapping("/usuario/editar")
    public String showEditForm(Model model) {
        // Usuario usuario = usuarioService.obtenerUsuarioConectado();
        Usuario usuario = new Usuario();
        // if (usuario != null) {
            model.addAttribute("usuarioForm", usuario);
            return "crudusuario/userEditView";
        // } else {
        //     return "redirect:/";
        // }
    }
    
    @PostMapping("/usuario/editar/submit")
    public String showEditSubmit(@Valid @ModelAttribute("usuarioForm") Usuario usuario,
            BindingResult bindingResult) {

        // if (!bindingResult.hasErrors()) 
        //     usuarioService.editar(usuario);
        return "redirect:/";
    }

    @GetMapping("/usuario/changepassword")
    public String showChangeForm(Model model) {
        // Usuario usuario = usuarioService.obtenerUsuarioConectado();
        Usuario usuario = new Usuario();
        // if (usuario != null) {
            model.addAttribute("usuarioForm", usuario);
            return "crudusuario/userChangePasswordView";
        // } else {
        //     return "redirect:/";
        // }
    }

    @PostMapping("/usuario/changepassword/submit")
    public String showChangeSubmit(@Valid @ModelAttribute("usuarioForm") Usuario usuario,
            BindingResult bindingResult) {

        // if (!bindingResult.hasErrors()) 
        //     usuarioService.editar(usuario);
        return "redirect:/";
    }

    @GetMapping("/usuario/borrarUser/{id}")
    public String showDeleteUser(@PathVariable Long id) {
        try{
            // usuarioService.borrarUsu(id);
            txterror = "Operación realizada con éxito";
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/usuario/userlist";
    }
    
}