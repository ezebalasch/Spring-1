/*
// Curso Egg FullStack
 */
package com.egg.news.controladores;

import com.egg.news.entidades.UsuarioRol;
import com.egg.news.servicios.UsuarioRolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ezequiel Balasch
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    UsuarioRolServicio usuarioRolServicio;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id){
        
        UsuarioRol usuarioRol = usuarioRolServicio.getOne(id);
        byte[] imagen = usuarioRol.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
}
