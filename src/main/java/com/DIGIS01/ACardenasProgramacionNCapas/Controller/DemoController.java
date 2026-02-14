/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//Anotación - 
@Controller // procesar interacciones de usuario
@RequestMapping("demo")
public class DemoController {

    @GetMapping("saludo")
    public String Test(@RequestParam String nombre, Model model) {
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }

    @GetMapping("saludo/{nombre}")
    public String Test2(@PathVariable("nombre") String nombre, Model model) {
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }

    @GetMapping("suma")
    public String suma(@RequestParam int numero, int numero2, Model model) {
        model.addAttribute("numero", numero);
        model.addAttribute("numero2", numero2);
        return "HolaMundo";
    }

    @GetMapping("multiplicacion")
    public String multiplicacion(@RequestParam int numero, int numero2, Model model) {
       
        model.addAttribute("numero", numero);
        model.addAttribute("numero2", numero2);
        return "HolaMundo";
    }
    
    @GetMapping("factorial")
    public String factorial(@RequestParam int numero, Model model) {
            int resultado = 1;
        for (int i = 1; i <= numero; i++) {
            resultado *= i;
        }
        model.addAttribute("numero", resultado);
        return "HolaMundo";
    }
    

    @GetMapping("usuario")
    public String user( Model model) {
        return "usuario";
    }
    
    @GetMapping("form")
    public String form( Model model) {
        return "form";
    }
    
    
    
    
}
