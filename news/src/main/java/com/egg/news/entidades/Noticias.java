/*
// Curso Egg FullStack
 */
package com.egg.news.entidades;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Ezequiel Balasch
 */
@Entity
@Table(name = "noticias")
public class Noticias {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String titulo;
    private String cuerpo;
    
    @Temporal(TemporalType.DATE)
    private Calendar fecha;
    
    private Boolean alta;
    
    @ManyToOne
    private Usuario usuario;

    public Noticias() {
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public Boolean getAlta() {
        return alta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
