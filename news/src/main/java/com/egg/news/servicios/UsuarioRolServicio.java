/*
// Curso Egg FullStack
 */
package com.egg.news.servicios;

import com.egg.news.entidades.Imagen;
import com.egg.news.entidades.UsuarioRol;
import com.egg.news.enumeraciones.Rol;
import com.egg.news.excepciones.MiException;
import com.egg.news.repositorios.UsuarioRolRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ezequiel Balasch
 */
@Service
public class UsuarioRolServicio implements UserDetailsService {

    @Autowired
    private UsuarioRolRepositorio usuarioRolRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiException {

        validar(nombre, email, password, password2);

        UsuarioRol usuarioRol = new UsuarioRol();

        usuarioRol.setNombre(nombre);
        usuarioRol.setEmail(email);
        usuarioRol.setPassword(new BCryptPasswordEncoder().encode(password));
        usuarioRol.setRol(Rol.USER);
        Imagen imagen = imagenServicio.guardar(archivo);
        usuarioRol.setImagen(imagen);
        usuarioRolRepositorio.save(usuarioRol);

    }

    public UsuarioRol getOne(String Id) {
        return usuarioRolRepositorio.getOne(Id);
    }

    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2) throws MiException {

        validar(nombre, email, password, password2);

        Optional<UsuarioRol> respuesta = usuarioRolRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {

            UsuarioRol usuarioRol = respuesta.get();

            usuarioRol.setNombre(nombre);
            usuarioRol.setEmail(email);
            usuarioRol.setPassword(new BCryptPasswordEncoder().encode(password));
            usuarioRol.setRol(Rol.USER);
            String idImagen = null;
            if (usuarioRol.getImagen() != null) {

                idImagen = usuarioRol.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            usuarioRol.setImagen(imagen);
            usuarioRolRepositorio.save(usuarioRol);
        }

    }

    public void adminEditar(MultipartFile archivo, String idUsuario, String nombre, String email) throws MiException {


        Optional<UsuarioRol> respuesta = usuarioRolRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {

            UsuarioRol usuarioRol = respuesta.get();

            usuarioRol.setNombre(nombre);
            usuarioRol.setEmail(email);
            String idImagen = null;
            if (usuarioRol.getImagen() != null) {

                idImagen = usuarioRol.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            usuarioRol.setImagen(imagen);
            usuarioRolRepositorio.save(usuarioRol);
        }

    }

    private void validar(String nombre, String email, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacío");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("La contraseña no puede ser nula o estar vacía");
        } else if (password.length() <= 7) {
            throw new MiException("La contraseña debe tener 8 o más digitos");

        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

    }

    public List<UsuarioRol> listarUsuarios() {

        List<UsuarioRol> usuarios = new ArrayList();

        usuarios = usuarioRolRepositorio.findAll();

        return usuarios;
    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<UsuarioRol> respuesta = usuarioRolRepositorio.findById(id);

        if (respuesta.isPresent()) {

            UsuarioRol usuario = respuesta.get();

            if (usuario.getRol().equals(Rol.USER)) {

                usuario.setRol(Rol.ADMIN);
            } else if (usuario.getRol().equals(Rol.ADMIN)) {
                usuario.setRol(Rol.USER);
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UsuarioRol usuarioRol = usuarioRolRepositorio.buscarPorEmail(email);

        if (usuarioRol != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuarioRol.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuarioRol);

            return new User(usuarioRol.getEmail(), usuarioRol.getPassword(), permisos);
        } else {
            return null;
        }

    }
}
