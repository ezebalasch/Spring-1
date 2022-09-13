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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private UsuarioRolServicio usuarioRolServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, HttpSession session) {
        UsuarioRol logueado = (UsuarioRol) session.getAttribute("usuariosession");
        UsuarioRol usuario = usuarioRolServicio.getOne(logueado.getId());
        modelo.put("usuario", usuario);

        return "noticias_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String titulo,
            @RequestParam(required = false) String id_usuario,
            @RequestParam(required = false) String cuerpo, ModelMap modelo) {
        try {

            System.out.println(id_usuario);
            noticiasServicio.crearNoticias(titulo, cuerpo, id_usuario);
            modelo.put("éxito", "La noticia fue cargada correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "noticias_form.html";
        }

        return "noticia_cargada.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Noticias> noticias = noticiasServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "noticias_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("noticia", noticiasServicio.getOne(id));
        List<UsuarioRol> usuarios = usuarioRolServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "noticias_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String titulo,
            String id_usuario, String cuerpo, ModelMap modelo) {
        try {
            noticiasServicio.modificarNoticias(id, titulo, cuerpo, id_usuario);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "noticias_modificar.html";
        }

    }
}
