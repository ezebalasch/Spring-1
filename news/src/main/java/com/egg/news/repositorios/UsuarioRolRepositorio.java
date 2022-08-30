/*
// Curso Egg FullStack
 */
package com.egg.news.repositorios;

import com.egg.news.entidades.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ezequiel Balasch
 */
@Repository
public interface UsuarioRolRepositorio extends JpaRepository<UsuarioRol, String> {

    @Query("SELECT u FROM UsuarioRol u WHERE u.email = :email")
    public UsuarioRol buscarPorEmail(@Param("email") String email);

}
