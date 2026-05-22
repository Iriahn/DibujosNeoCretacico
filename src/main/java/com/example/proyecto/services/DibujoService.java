package com.example.proyecto.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.proyecto.domain.CategoriaEnum;
import com.example.proyecto.domain.Usuario;
import com.example.proyecto.dto.DibujoDto;
import com.example.proyecto.domain.Dibujo;
import com.example.proyecto.domain.EstiloEnum;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
public class DibujoService {

    private final ModelMapper modelMapper;

    public Integer anho(){
        return LocalDate.now().getYear();
    }

    public List<String> listaTipos(){
        return new ArrayList<>(Arrays.asList("Logo", "Póster", "Normal (A4)", "Bandera"));
    }

    //Obtener del servicio del 400
    // public List<Dibujo> obtenerTodos(){
    //     return dibrepositorio.findAll();
    // }

    public CategoriaEnum[] obtenerCategorias(){
        return CategoriaEnum.values();
    }

    public EstiloEnum[] obtenerEstilos(){
        return EstiloEnum.values();
    }

    //Buscar en el servicio del 400
    // public Dibujo obtenerPorId(Long id) throws RuntimeException{
    //     return dibrepositorio.findById(id).orElseThrow(() -> new RuntimeException("Dibujo no encontrado"));  
    // }

    public CategoriaEnum obtenerCategoria(String id) throws RuntimeException{
        CategoriaEnum[] categorias = obtenerCategorias();
        for (CategoriaEnum c : categorias) {
            if(c.name().equals(id))
                return c;
            else{
                throw new RuntimeException("Categoría no encontrada");  
            }
        }
        return null;
    }

    public EstiloEnum obtenerEstilo(String id) throws RuntimeException{
        EstiloEnum[] estilos = obtenerEstilos();
        for (EstiloEnum e : estilos) {
            if(e.name().equals(id))
                return e;
            else{
                throw new RuntimeException("Estilo no encontrado");  
            }
        }
        return null;    
    }

    public Usuario obtenerUsuario(String nombre){
        return new Usuario();
    }

    //Añadir con el servicio del 400
    // public boolean anadir(Dibujo dibujo) {
    //     try{
    //         dibrepositorio.save(dibujo);
    //         return true;
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error al añadir dibujo");
    //     }
    // }

    //Editar con el servicio del 400
    // public Dibujo editar(Dibujo dibujo) throws RuntimeException{
    //     obtenerPorId(dibujo.getId());
    //     return dibrepositorio.save(dibujo);
    // }

    //Borrar con el servicio del 400
    // public void borrar(Long id) throws RuntimeException{
    //     obtenerPorId(id);
    //     dibrepositorio.deleteById(id);
    // }

    public List<DibujoDto> toDto(List<Dibujo> lista){
        List<DibujoDto> dibdto = new ArrayList<>();
        for(Dibujo d : lista){
            dibdto.add(modelMapper.map(d, DibujoDto.class));
        }
        return dibdto;
    }

}
