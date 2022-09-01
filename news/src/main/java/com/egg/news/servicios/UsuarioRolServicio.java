/*
// Curso Egg FullStack
 */
package com.egg.news.servicios;

import com.egg.news.entidades.UsuarioRol;
import com.egg.news.enumeraciones.Rol;
import com.egg.news.excepciones.MiException;
import com.egg.news.repositorios.UsuarioRolRepositorio;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author Ezequiel Balasch
 */
@Service
public class UsuarioRolServicio implements UserDetailsService{
    
    @Autowired
    private UsuarioRolRepositorio usuarioRolepositorio;
    
    @Transactional
    public void registrar(String nombre, String email, String password, String password2) throws MiException{
        
        validar (nombre, email, password, password2);
        
        UsuarioRol usuarioRol = new UsuarioRol();
        
        usuarioRol.setNombre(nombre);
        usuarioRol.setEmail(email);
        usuarioRol.setPassword(new BCryptPasswordEncoder().encode(password));
        usuarioRol.setRol(Rol.USER);
        
        usuarioRolepositorio.save(usuarioRol);
        
    }
    
    private void validar(String nombre, String email, String password, String password2) throws MiException{
    
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacío");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("La contraseña no puede ser nula o estar vacía");
        }else if (password.length() <= 7) {
            throw new MiException("La contraseña debe tener 8 o más digitos");
            
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UsuarioRol usuarioRol = usuarioRolepositorio.buscarPorEmail(email);
        
        if (usuarioRol != null) {
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuarioRol.getRol().toString());
            
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", usuarioRol);
            
            return new User(usuarioRol.getEmail(), usuarioRol.getPassword(), permisos);
        }else{
            return null;
        }
        
    }
    
}
