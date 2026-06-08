package com.example.proyecto.controllers;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.proyecto.domain.CategoriaEnum;
import com.example.proyecto.domain.Dibujo;
import com.example.proyecto.domain.SubCategoriaEnum;
import com.example.proyecto.services.AS400Service;
import com.example.proyecto.services.FileService;
import com.example.proyecto.services.MainService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class DrawingController {

    private final FileService fileService;
    private final MainService mainService;
    private final AS400Service as400Service;
    private String txterror;
    private String imagen;

    @GetMapping("/dibujo/drawlist")
    public String galeria(Model model) {
        model.addAttribute("findForm", new Dibujo());
        model.addAttribute("listatecnicas", mainService.obtenerCategorias());
        if(txterror != null)
            model.addAttribute("error", txterror);
        txterror = null;
        return "cruddibujo/drawListView";
    }

    @GetMapping("/dibujo/new")
    public String nuevodibujo(Model model) {
        model.addAttribute("dibujo", new Dibujo());
        return "cruddibujo/newDrawView";
    }

    @PostMapping("/dibujo/new/submit")
    public String index(@Valid Dibujo dibujo, BindingResult bindingresult, @RequestParam(required = false) MultipartFile file) {
        try{
            if(bindingresult.hasErrors()){
                txterror = "Error en el procesado";
            }else{
                if(file == null || file.isEmpty()){
                    dibujo.setImagen("provisional.png");
                }else{
                    String storedFileName = fileService.storeFile(file);
                    dibujo.setImagen(storedFileName);
                }
                as400Service.crearDibujo(dibujo);
            }
        }catch(RuntimeException ex){
            txterror = ex.getMessage();
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/dibujo/drawlist";
    }
    
    @GetMapping("/dibujo/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Map<String, Object> dib = as400Service.obtenerDibujo(id);
            Dibujo dibujo = new Dibujo(new BigDecimal(dib.get("IDDIB").toString()), dib.get("TITULO").toString(), dib.get("TEMATICA").toString(), dib.get("DESCRIPCION").toString(), dib.get("FINALIDAD").toString(), CategoriaEnum.valueOf(dib.get("CATEGORIA").toString().toUpperCase()), SubCategoriaEnum.valueOf(dib.get("SUBCATEGORIA").toString().toUpperCase()), Integer.parseInt(dib.get("ANHO").toString()), new BigDecimal(dib.get("PRECIO").toString()), dib.get("IMAGEN").toString(), Boolean.parseBoolean(dib.get("COMPLETADO").toString()));
            imagen = dibujo.getImagen();
            model.addAttribute("dibujo", dibujo);
            return "cruddibujo/editDrawView";
        } catch (Exception e) {
            txterror = e.getMessage();
            return "redirect:/dibujo/drawlist";
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
                    String storedFileName = fileService.storeFile(file);
                    dibujo.setImagen(storedFileName);
                }
                as400Service.actualizarDibujo(dibujo);
                txterror = "Operación realizada con éxito";
            }
            return "redirect:/dibujo/drawlist";
        }catch(Exception e){
            txterror = e.getMessage();
            return "redirect:/dibujo/drawlist";
        }
    }

    @GetMapping("/dibujo/borrar/{id}")
    public String showDelete(@PathVariable Long id) {
        try{
            as400Service.eliminarDibujo(id);
            txterror = "Operación realizada con éxito";
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/dibujo/drawlist";
    }
}