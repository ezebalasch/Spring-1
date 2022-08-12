/*
// Curso Egg FullStack
 */
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ezequiel Balasch
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {
    @Autowired
    private LibroServicio libroservicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
       return "libro_form.html" ;
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam(required=false) Long isbn, @RequestParam String titulo,
            @RequestParam(required=false) Integer ejemplares, @RequestParam String idAutor, 
            @RequestParam String idEditorial, ModelMap modelo) {
        try {
            libroservicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro fue cargado correctamente");
        }catch (MiException ex){
            modelo.put("error", ex.getMessage());
            return "libro_form.html";
        }
        return "index.html";
    }
    
}
