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
import com.example.proyecto.domain.EstadoPrintEnum;
import com.example.proyecto.domain.Print;
import com.example.proyecto.domain.SubCategoriaEnum;
import com.example.proyecto.services.AS400Service;
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
    private final AS400Service as400Service;
    private String txterror;
    private String imagen;

    @GetMapping("/prints/list")
    public String galeria(Model model) {
        if(txterror != null)
            model.addAttribute("error", txterror);
        txterror = null;
        return "crudprints/printsListView";
    }
    
    @GetMapping("/prints/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Map<String, Object> pri = as400Service.obtenerPrint(id);
            if(pri != null){
                Print print = new Print(new BigDecimal(pri.get("IDPRI").toString()), pri.get("NOMBRE").toString(), pri.get("REDPREFERIDA").toString(), pri.get("USERPREFERIDA").toString(), new BigDecimal(pri.get("IDDIB").toString()), Integer.parseInt(pri.get("UNIDADES").toString()), pri.get("TAMANO").toString(), pri.get("TIPO").toString(), pri.get("COMENTARIOS").toString(), new BigDecimal(pri.get("PRECIO").toString()), EstadoPrintEnum.valueOf(pri.get("ESTADO").toString().replace(" ", "")));
                model.addAttribute("print", print);
            }else{
                txterror = "Print no encontrada";
                return "redirect:/prints/list";
            }
            return "crudprints/editPrintsView";
        } catch (Exception e) {
            txterror = e.getMessage();
            return "redirect:/prints/list";
        }
    }

    @PostMapping("/prints/editar/{id}/submit")
    public String showEditSubmit(@PathVariable Long id, @Valid Print print, BindingResult bindingResult) {
        try{
            if (bindingResult.hasErrors()) {
                txterror = "Error en formulario";
            }else{
                as400Service.actualizarPrint(print);
                txterror = "Operación realizada con éxito";
            }
            return "redirect:/prints/list";
        }catch(Exception e){
            txterror = e.getMessage();
            return "redirect:/prints/list";
        }
    }

    @GetMapping("/prints/borrar/{id}")
    public String showDelete(@PathVariable Long id) {
        try{
            as400Service.eliminarPrint(id);
            txterror = "Operación realizada con éxito";
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/prints/list";
    }
}