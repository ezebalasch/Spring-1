/*
// Curso Egg FullStack
 */
package com.egg.news.repositorios;

import com.egg.news.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ezequiel Balasch
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
    
}
