/*
// Curso Egg FullStack
 */
package com.egg.news.controladores;

import com.egg.news.entidades.Noticias;
import com.egg.news.entidades.UsuarioRol;
import com.egg.news.excepciones.MiException;
import com.egg.news.servicios.NoticiasServicio;
import com.egg.news.servicios.UsuarioRolServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ezequiel Balasch
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private NoticiasServicio noticiasServicio;

    @Autowired
    private UsuarioRolServicio usuarioRolServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {
        List<Noticias> noticias = noticiasServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<UsuarioRol> usuarios = usuarioRolServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuarioRol_list.html";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id) {
        usuarioRolServicio.cambiarRol(id);
        return "redirect:/admin/usuarios";
    }



}
