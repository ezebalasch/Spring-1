/*
// Curso Egg FullStack
 */
package com.egg.news.controladores;

import com.egg.news.entidades.Imagen;
import com.egg.news.entidades.Noticias;
import com.egg.news.entidades.UsuarioRol;
import com.egg.news.excepciones.MiException;
import com.egg.news.servicios.ImagenServicio;
import com.egg.news.servicios.NoticiasServicio;
import com.egg.news.servicios.UsuarioRolServicio;
import java.util.List;
import java.util.Optional;
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
    private ImagenServicio imagenServicio;
    
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

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioRolServicio.registrar(archivo, nombre, email, password, password2);
            modelo.put("éxito", "Usuario registrado correctamente!");
            return "usuarioRol_cargado.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }

    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        UsuarioRol usuario = usuarioRolServicio.getOne(id);
        modelo.put("usuario", usuario);
        return "editar_usuario.html";
    }

    @PostMapping("/modificar/{id}")
    public String actualizar(@RequestParam MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String email,
             ModelMap modelo) throws MiException{
        
        try {
            usuarioRolServicio.adminEditar(archivo, id, nombre, email);
            modelo.put("exito", "Usuario actualizado correctamente!");
            return "panel.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            
            return "editar_usuario.html";
        }
        
    }

}
