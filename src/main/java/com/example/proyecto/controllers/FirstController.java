package com.example.proyecto.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.proyecto.domain.Dibujo;
import com.example.proyecto.domain.ContactForm;
import com.example.proyecto.services.FileService;
import com.example.proyecto.services.DibujoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class FirstController {

    private final FileService fileService;
    private final DibujoService dibujoService;
    private String txterror;
    private String imagen;

    @GetMapping({"/index", "/home", "/"})
    public String index(@RequestParam(required = false) String usuario, Model model) {
        model.addAttribute("anho", dibujoService.anho());
        model.addAttribute("usuario", usuario);
        return "principal/indexView";
    }

    @GetMapping("/drawlist")
    public String galeria(Model model) {
        model.addAttribute("listacategorias", dibujoService.obtenerCategorias());
        // model.addAttribute("listadibujos", dibujoService.toDto(dibujoService.obtenerTodos()));
        if(txterror != null)
            model.addAttribute("error", txterror);
        return "cruddibujo/drawListView";
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
       Resource file = fileService.loadAsResource(filename);
       return ResponseEntity.ok().body(file);
    }

    // @GetMapping("/dibujo/{id}")
    // public String detalle(@PathVariable Long id, Model model) {
    //     try {
    //         Dibujo dibujo = dibujoService.obtenerPorId(id);
    //         model.addAttribute("dibujo", dibujo);
    //         return "cruddibujo/drawView";
    //     } catch (Exception e) {
    //         txterror = e.getMessage();
    //         return "redirect:/drawlist";
    //     }
    // }

    @GetMapping("/new")
    public String nuevodibujo(Model model) {
        model.addAttribute("dibujo", new Dibujo());
        model.addAttribute("anho", dibujoService.anho());
        model.addAttribute("listaestilos", dibujoService.obtenerEstilos());
        model.addAttribute("listacategorias", dibujoService.obtenerCategorias());
        return "cruddibujo/newView";
    }

    // @PostMapping("/new/submit")
    // public String index(@Valid Dibujo dibujo, BindingResult bindingresult, @RequestParam MultipartFile file) {
    //     try{
    //         if(bindingresult.hasErrors())
    //             txterror = "Error en el procesado";
    //         else{
    //             dibujoService.anadir(dibujo);
    //             String storedFileName = fileService.storeFile(file, Long.toString(dibujo.getId()));
    //             dibujo.setImagen(storedFileName);
    //             dibujoService.editar(dibujo);
    //         }
    //     }catch(RuntimeException ex){
    //         txterror = ex.getMessage();
    //     }catch(Exception e){
    //         txterror = e.getMessage();
    //     }
    //     return "redirect:/drawlist";
    // }

    @GetMapping("/reestablecer")
    public String showAll() {
        return "redirect:/drawlist";
    }

    // @GetMapping("/editar/{id}")
    // public String showEditForm(@PathVariable Long id, Model model) {
    //     try {
    //         Dibujo dibujo = dibujoService.obtenerPorId(id);
    //         imagen = dibujo.getImagen();
    //         model.addAttribute("dibujo", dibujo);
    //         model.addAttribute("listaestilos", dibujoService.obtenerEstilos());
    //         model.addAttribute("listacategorias", dibujoService.obtenerCategorias());
    //         return "cruddibujo/editFormView";
    //     } catch (Exception e) {
    //         txterror = e.getMessage();
    //         return "redirect:/drawlist";
    //     }
    // }

    // @PostMapping("/editar/{id}/submit")
    // public String showEditSubmit(@PathVariable Long id, @Valid Dibujo dibujo, BindingResult bindingResult, @RequestParam MultipartFile file) {
    //     try{
    //         if (bindingResult.hasErrors()) {
    //             txterror = "Error en formulario";
    //         }else{
    //             if(file.isEmpty() || file == null){
    //                 dibujo.setImagen(imagen);
    //             }
    //             else{
    //                 String storedFileName = fileService.storeFile(file, Long.toString(dibujo.getId()));
    //                 dibujo.setImagen(storedFileName);
    //             }
    //             dibujoService.editar(dibujo);
    //             txterror = "Operación realizada con éxito";
    //         }
    //         return "redirect:/drawlist";
    //     }catch(Exception e){
    //         txterror = e.getMessage();
    //         return "redirect:/drawlist";
    //     }
    // }

    // @GetMapping("/borrar/{id}")
    // public String showDelete(@PathVariable Long id) {
    //     try{
    //         dibujoService.borrar(id);
    //         txterror = "Operación realizada con éxito";
    //     }catch(Exception e){
    //         txterror = e.getMessage();
    //     }
    //     return "redirect:/drawlist";
    // }

    @GetMapping("/lista")
    public String lista(Model model) {
        model.addAttribute("listaEstilo", dibujoService.obtenerEstilos());
        model.addAttribute("listaTipo", dibujoService.listaTipos());
        model.addAttribute("anho", dibujoService.anho());
        return "principal/listaView";
    }

    @GetMapping({"/enlaces", "/enlaces-externos"})
    public String enlaces(Model model) {
        model.addAttribute("anho", dibujoService.anho());
        return "principal/enlaces-externosView";
    }

    @GetMapping({"/form", "/formulario"})
    public String formulario(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        if(txterror != null)
            model.addAttribute("error", txterror);
        return "principal/formularioView";
    }

    @PostMapping("/contact/submit")
    public String index(ContactForm forminfo) {
        try{
            txterror = "Formulario procesado correctamente";
        }catch(RuntimeException ex){
            txterror = ex.getMessage();
        }catch(Exception e){
            txterror = e.getMessage();
        }
        return "redirect:/form";
    }
    
}