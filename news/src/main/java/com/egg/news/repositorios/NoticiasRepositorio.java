/*
// Curso Egg FullStack
 */
package com.egg.news.repositorios;

import com.egg.news.entidades.Noticias;
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
public interface NoticiasRepositorio extends JpaRepository<Noticias, String> {

    @Query("SELECT n FROM Noticias n WHERE n.titulo = :titulo")
    public Noticias buscarPorTitulo(@Param("titulo") String titulo);
    
    @Query("SELECT n FROM Noticias n WHERE n.usuario.nombre = :nombre")
    public  List<Noticias> buscarPorUsuario(@Param("nombre") String nombre);
    
}
