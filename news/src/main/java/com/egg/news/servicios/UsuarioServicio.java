/*
// Curso Egg FullStack
 */
package com.egg.news.servicios;

import com.egg.news.entidades.Usuario;
import com.egg.news.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ezequiel Balasch
 */
@Service
public class UsuarioServicio {
    
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public void crearUsuario(String nombre, String apellido) {
        
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        
        usuarioRepositorio.save(usuario);
    }
    
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.findAll();
        
        return usuarios;
    }
    
    @Transactional
    public void modificarUsuario(String id, String nombre, String apellido) {
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuarioRepositorio.save(usuario);
        }
    }
}
