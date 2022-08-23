/*
// Curso Egg FullStack
 */
package com.egg.news.controladores;

import com.egg.news.excepciones.MiException;
import com.egg.news.servicios.NoticiasServicio;
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
@RequestMapping("/noticias")
public class NoticiasControlador {

    @Autowired
    private NoticiasServicio noticiasServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "noticias_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String id_usuario,
            @RequestParam String cuerpo, ModelMap modelo) {
        try {
            noticiasServicio.crearNoticias(titulo, cuerpo, id_usuario);
            modelo.put("éxito", "La noticia fue cargada correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        return "noticias_form.html";
        }
        return "index.html";
    }
}
