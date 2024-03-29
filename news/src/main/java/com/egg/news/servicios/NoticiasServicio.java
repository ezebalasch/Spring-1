/*
// Curso Egg FullStack
 */
package com.egg.news.servicios;

import com.egg.news.entidades.Noticias;
import com.egg.news.entidades.UsuarioRol;
import com.egg.news.excepciones.MiException;
import com.egg.news.repositorios.NoticiasRepositorio;
import com.egg.news.repositorios.UsuarioRolRepositorio;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
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
    UsuarioRolRepositorio usuarioRolRepositorio;

    @Transactional
    public void crearNoticias(String titulo, String cuerpo, String idUsuario) throws MiException {
        validar(titulo, cuerpo, idUsuario);
        UsuarioRol usuarioRol = usuarioRolRepositorio.findById(idUsuario).get();
        Noticias noticias = new Noticias();

        noticias.setTitulo(titulo);
        noticias.setCuerpo(cuerpo);
        noticias.setUsuarioRol(usuarioRol);
        noticias.setAlta(Boolean.TRUE);
        noticias.setFecha(Calendar.getInstance());

        noticiasRepositorio.save(noticias);
    }

    @Transactional(readOnly = true)
    public List<Noticias> listarNoticias() {
        List<Noticias> noticias = new ArrayList();
        noticias = noticiasRepositorio.getAllNews();

        return noticias;
    }

    @Transactional
    public void modificarNoticias(String id, String titulo, String cuerpo, String idUsuario) throws MiException {

        validar(titulo, cuerpo, idUsuario);
        Optional<Noticias> respuesta = noticiasRepositorio.findById(id);
        Optional<UsuarioRol> respuestaUsuario = usuarioRolRepositorio.findById(idUsuario);
        UsuarioRol usuarioRol = new UsuarioRol();
        if (respuestaUsuario.isPresent()) {
            usuarioRol = respuestaUsuario.get();
        }
        if (respuesta.isPresent()) {
            Noticias noticias = respuesta.get();
            noticias.setTitulo(titulo);
            noticias.setCuerpo(cuerpo);
            noticias.setUsuarioRol(usuarioRol);
            noticiasRepositorio.save(noticias);
        }
    }

    @Transactional(readOnly = true)
    public Noticias getNewsById(String id) throws MiException {
        validarId(id);
        Optional<Noticias> optNews = noticiasRepositorio.findById(id);
        if (!optNews.isPresent()) {
            throw new MiException("No se encontraron noticias");
        }
        return optNews.get();
    }

    @Transactional
    public void eliminarNoticia(String id) throws MiException {
        Noticias noticia = getNewsById(id);
        
        noticia.setAlta(Boolean.FALSE);
        noticiasRepositorio.save(noticia);
    }

    public Noticias getOne(String id) {
        return noticiasRepositorio.getOne(id);

    }

    private void validarId(String id) throws MiException {

        if (id.isEmpty() || id == null) {
            throw new MiException("El ID está vacío");
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
