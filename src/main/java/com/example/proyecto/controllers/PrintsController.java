package com.example.proyecto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.proyecto.domain.Dibujo;
import com.example.proyecto.services.FileService;
import com.example.proyecto.services.MainService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class PrintsController {

    private final FileService fileService;
    private final MainService dibujoService;
    private String txterror;
    private String imagen;

    @GetMapping("/prints/list")
    public String galeria(Model model) {
        model.addAttribute("findForm", new Dibujo());
        model.addAttribute("listatecnicas", dibujoService.obtenerCategorias());
        if(txterror != null)
            model.addAttribute("error", txterror);
        txterror = null;
        return "cruddibujo/drawListView";
    }
    
    @GetMapping("/prints/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            // Dibujo dibujo = dibujoService.obtenerPorId(id);
            Dibujo dibujo = new Dibujo();
            imagen = dibujo.getImagen();
            model.addAttribute("dibujo", dibujo);
            model.addAttribute("listaestilos", dibujoService.obtenerSubCategorias());
            model.addAttribute("listatecnicas", dibujoService.obtenerCategorias());
            return "cruddibujo/editFormView";
        } catch (Exception e) {
            txterror = e.getMessage();
            return "redirect:/public/drawlist";
        }
    }

    @PostMapping("/prints/editar/{id}/submit")
    public String showEditSubmit(@PathVariable Long id, @Valid Dibujo dibujo, BindingResult bindingResult, @RequestParam MultipartFile file) {
        try{
            if (bindingResult.hasErrors()) {
                txterror = "Error en formulario";
            }else{
                if(file.isEmpty() || file == null){
                    dibujo.setImagen(imagen);
                }
                else{
                    String storedFileName = fileService.storeFile(file);
                    dibujo.setImagen(storedFileName);
                }
                // dibujoService.editar(dibujo);
                txterror = "Operación realizada con éxito";
            }
            return "redirect:/public/drawlist";
        }catch(Exception e){
            txterror = e.getMessage();
            return "redirect:/public/drawlist";
        }
    }

    @GetMapping("/prints/borrar/{id}")
    public String showDelete(@PathVariable Long id) {
        try{
            // dibujoService.borrar(id);
            txterror = "Operación realizada con éxito";
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/public/drawlist";
    }
}