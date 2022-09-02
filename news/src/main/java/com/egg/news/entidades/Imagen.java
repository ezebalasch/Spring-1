/*
// Curso Egg FullStack
 */
package com.egg.news.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Ezequiel Balasch
 */
@Entity
public class Imagen {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String mime;
    
    private String nombre;
    
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

    public Imagen() {
    }

    public String getId() {
        return id;
    }

    public String getMime() {
        return mime;
    }

    public String getNombre() {
        return nombre;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
    
    
    
    
}
