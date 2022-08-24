/*
// Curso Egg FullStack
 */
package com.egg.news.servicios;

import com.egg.news.entidades.Noticias;
import com.egg.news.entidades.Usuario;
import com.egg.news.excepciones.MiException;
import com.egg.news.repositorios.NoticiasRepositorio;
import com.egg.news.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Calendar;
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
public class NoticiasServicio {

    @Autowired
    NoticiasRepositorio noticiasRepositorio;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearNoticias(String titulo, String cuerpo, String idUsuario) throws MiException {
        validar(titulo, cuerpo, idUsuario);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        Noticias noticias = new Noticias();
        System.out.println(usuario.toString());
        noticias.setTitulo(titulo);
        noticias.setCuerpo(cuerpo);
        noticias.setUsuario(usuario);
        noticias.setAlta(Boolean.TRUE);
        noticias.setFecha(Calendar.getInstance());

        noticiasRepositorio.save(noticias);
    }

    public List<Noticias> listarNoticias() {
        List<Noticias> noticias = new ArrayList();
        noticias = noticiasRepositorio.findAll();

        return noticias;
    }

    @Transactional
    public void modificarNoticias(String id, String titulo, String cuerpo, String idUsuario) throws MiException {

        validar(titulo, cuerpo, idUsuario);
        Optional<Noticias> respuesta = noticiasRepositorio.findById(id);
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(idUsuario);
        Usuario usuario = new Usuario();
        if (respuestaUsuario.isPresent()) {
            usuario = respuestaUsuario.get();
        }
        if (respuesta.isPresent()) {
            Noticias noticias = respuesta.get();
            noticias.setTitulo(titulo);
            noticias.setCuerpo(cuerpo);
            noticias.setUsuario(usuario);
            noticiasRepositorio.save(noticias);
        }
    }

    private void validar(String titulo, String cuerpo, String idUsuario) throws MiException {

        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no puede estar vacío");
        }
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("El cuerpo no puede estar vacío");
        }
        if (idUsuario.isEmpty() || idUsuario == null) {
            throw new MiException("El usuario no puede estar vacío");
        }
    }
}
