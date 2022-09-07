/*
// Curso Egg FullStack
 */
package com.egg.news.entidades;

import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="usuarioRol", nullable=false)
    private UsuarioRol usuarioRol;

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

    public UsuarioRol getUsuarioRol() {
        return usuarioRol;
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

    public void setUsuarioRol(UsuarioRol usuarioRol) {
        this.usuarioRol = usuarioRol;
    }

    @Override
    public String toString() {
        return "Noticias{" + "id=" + id + ", titulo=" + titulo + ", cuerpo=" + cuerpo + ", fecha=" + fecha + ", alta=" + alta + ", usuarioRol=" + usuarioRol + '}';
    }

    

   

    

    
    
}
