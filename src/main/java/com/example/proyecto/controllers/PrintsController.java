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

    @GetMapping("/dibujo/drawlist")
    public String galeria(Model model) {
        model.addAttribute("findForm", new Dibujo());
        model.addAttribute("listatecnicas", dibujoService.obtenerCategorias());
        if(txterror != null)
            model.addAttribute("error", txterror);
        txterror = null;
        return "cruddibujo/drawListView";
    }

    @GetMapping("/dibujo/new")
    public String nuevodibujo(Model model) {
        model.addAttribute("dibujo", new Dibujo());
        model.addAttribute("anho", dibujoService.anho());
        model.addAttribute("listaestilos", dibujoService.obtenerEstilos());
        model.addAttribute("listatecnicas", dibujoService.obtenerCategorias());
        return "cruddibujo/newView";
    }

    @PostMapping("/dibujo/new/submit")
    public String index(@Valid Dibujo dibujo, BindingResult bindingresult, @RequestParam MultipartFile file) {
        try{
            if(bindingresult.hasErrors())
                txterror = "Error en el procesado";
            else{
                // dibujoService.anadir(dibujo);
                String storedFileName = fileService.storeFile(file, Long.toString(dibujo.getId()));
                dibujo.setImagen(storedFileName);
                // dibujoService.editar(dibujo);
            }
        }catch(RuntimeException ex){
            txterror = ex.getMessage();
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/public/drawlist";
    }
    
    @GetMapping("/dibujo/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            // Dibujo dibujo = dibujoService.obtenerPorId(id);
            Dibujo dibujo = new Dibujo();
            imagen = dibujo.getImagen();
            model.addAttribute("dibujo", dibujo);
            model.addAttribute("listaestilos", dibujoService.obtenerEstilos());
            model.addAttribute("listatecnicas", dibujoService.obtenerCategorias());
            return "cruddibujo/editFormView";
        } catch (Exception e) {
            txterror = e.getMessage();
            return "redirect:/public/drawlist";
        }
    }

    @PostMapping("/dibujo/editar/{id}/submit")
    public String showEditSubmit(@PathVariable Long id, @Valid Dibujo dibujo, BindingResult bindingResult, @RequestParam MultipartFile file) {
        try{
            if (bindingResult.hasErrors()) {
                txterror = "Error en formulario";
            }else{
                if(file.isEmpty() || file == null){
                    dibujo.setImagen(imagen);
                }
                else{
                    String storedFileName = fileService.storeFile(file, Long.toString(dibujo.getId()));
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

    @GetMapping("/dibujo/borrar/{id}")
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