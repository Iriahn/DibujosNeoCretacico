package com.example.proyecto.controllers;

import java.math.BigDecimal;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.proyecto.services.AS400Service;
import com.example.proyecto.services.FileService;

import jakarta.validation.Valid;

import com.example.proyecto.domain.Print;
import com.example.proyecto.services.MainService;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class MainController {

    private final FileService fileService;
    private final AS400Service as400Service;
    private String txterror;

    @GetMapping({"/index", "/home", "/"})
    public String index() {
        return "principal/indexView";
    }

    @GetMapping({"/portfolio", "/portafolio", "/drawlist"})
    public String galeria() {
        return "principal/portfolioView";
    }
    
    @GetMapping({"/encargo", "/encargos", "/comision", "/comission"})
    public String encargos(Model model) {
        if(txterror != null){
            model.addAttribute("error", txterror);
            txterror = null;
        }
        model.addAttribute("print", new Print());
        return "principal/encargosView";
    }

    @PostMapping("/print/submit")
    public String pedirprint(@Valid Print print, BindingResult bindingresult) {
        Boolean grande = true;
        Long unidad;
        try{
            if(bindingresult.hasErrors())
                txterror = "Error en el procesado";
            else{
                if(print.getTamano().equals("A5")){
                    grande = false;
                }
                if(grande && print.getTipo().equals("brillante"))
                    unidad = 9l;
                else if(!grande && print.getTipo().equals("mate"))
                    unidad = 5l;
                else
                    unidad = 7l;
                print.setPrecio(new BigDecimal(unidad*print.getUnidades()));
                // dar de alta print
                txterror = "Pedido registrado satisfactoriamente";
                as400Service.crearPrint(print);
            }
        }catch(RuntimeException ex){
            txterror = ex.getMessage();
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/encargo";
    }

    @GetMapping({"/contacto", "/contact"})
    public String contacto(Model model) {
        return "principal/contactoView";
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
       Resource file = fileService.loadAsResource(filename);
       return ResponseEntity.ok().body(file);
    }

    @GetMapping("/gestionar")
    public String showManagePage() {
        return "gestionView";
    }
    
    @GetMapping("/accessError")
    public String showAccessErrorPage() {
        return "error/accessErrorPage";
    }
    
}