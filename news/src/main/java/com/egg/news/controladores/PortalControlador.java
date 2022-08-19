/*
// Curso Egg FullStack
 */
package com.egg.news.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ezequiel Balasch
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    @GetMapping("/")
    public String index(){
     
        return "index.html";
    }
}
