/*
// Curso Egg FullStack
 */
package com.egg.news.controladores;

import com.egg.news.entidades.Noticias;
import com.egg.news.servicios.NoticiasServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ezequiel Balasch
 */
@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private NoticiasServicio noticiasServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        List<Noticias> noticias = noticiasServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "index.html";
    }
}
