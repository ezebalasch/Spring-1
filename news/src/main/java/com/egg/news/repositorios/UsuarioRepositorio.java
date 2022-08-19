/*
// Curso Egg FullStack
 */
package com.egg.news.repositorios;

import com.egg.news.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ezequiel Balasch
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    public List<Usuario> busquedaPorNombreUsuario(@Param("nombre") String nombre);
    
    
}
