/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.RestController;

import com.DIGIS01.ACardenasProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.UsuarioDAOJPAImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author digis
 */
@RestController
@RequestMapping("api/demo")
public class DemosRestController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @GetMapping
    public String Holamundo() {
        return "holamundo";
    }

    @GetMapping("saludo/{nombre}")
    public String saludo(@PathVariable("nombre") String nombre) {
        return "hola " + nombre;
    }

    @GetMapping("saludo")
    public String saludo2(@RequestParam("nombre") String nombre) {
        return "saludos " + nombre;
    }

    @GetMapping("datos/{status}")
    public ResponseEntity obtenerdatos(@PathVariable("status") int status) {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario(null, "cardenas", "rizo", null, "7441580319", "arturito@gmail.com", "magenta89", "asd123", "Ma", "7441580319", "CARA030303HGRRZRA3", null, 1));
        return ResponseEntity.status(status).body(usuarios);
    }

    //suma de dos elementos con  get
    @GetMapping("suma/{elemento1}/{elemento2}")
    public String sumaElementos(@PathVariable("elemento1") int elemento1, @PathVariable("elemento2") int elemento2) {
        int resultado = elemento1 + elemento2;
        return "la suma da: " + resultado;
    }
    //suma con arreglo
    @PostMapping("sumaArreglo")
    public ResponseEntity sumaArreglo(@RequestBody int[] numeros) {
        int resultado = Arrays.stream(numeros).sum();
        return ResponseEntity.ok(resultado);
    }
    //lista de usuarios
    @GetMapping("usuarios")
    public Result usuarios() {
        Result result = new Result();
        result = usuarioDAOImplementation.GetAll();
        return result;
    }

}
