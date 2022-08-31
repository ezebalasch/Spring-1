/*
// Curso Egg FullStack
 */
package com.egg.news.controladores;

import com.egg.news.entidades.Noticias;
import com.egg.news.excepciones.MiException;
import com.egg.news.servicios.NoticiasServicio;
import com.egg.news.servicios.UsuarioRolServicio;
import java.util.List;
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
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private NoticiasServicio noticiasServicio;

    @Autowired
    private UsuarioRolServicio usuarioRolServicio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioRolServicio.registrar(nombre, email, password, password2);
            modelo.put("éxito", "Usuario registrado correctamente!");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos");
        }
        return "login.html";
    }

    @GetMapping("/inicio")
    public String inicio(ModelMap modelo) {
        List<Noticias> noticias = noticiasServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "inicio.html";
    }
}
