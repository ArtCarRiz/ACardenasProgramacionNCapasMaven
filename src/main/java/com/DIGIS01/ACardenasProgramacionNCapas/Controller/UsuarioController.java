/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.Controller;

import com.DIGIS01.ACardenasProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.PaisDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.RolDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Estado;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario;
import jakarta.validation.Valid;
import java.io.File;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author digis
 */
@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private RolDAOImplementation rolDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    @GetMapping
    public String Index(Model model) {
        Result result = usuarioDAOImplementation.GetAll();

        model.addAttribute("usuarios", result.objects);
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

        return "usuario";

    }

//    @GetMapping("form")
//    public String Accion(Model model) {
//        return "Form";
//    }
    @GetMapping("form")
    public String Accion(Model model) {
        model.addAttribute("usuario", new Usuario());

        Result resultPaises = paisDAOImplementation.GetAll();
        model.addAttribute("paises", resultPaises.objects);

        Result resultRol = rolDAOImplementation.GetAll();
        model.addAttribute("roles", resultRol.objects);

        return "Form";
    }

    @PostMapping("form")                                                                                                        //del model vienen todas las modificaciones
    public String Accion(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, @RequestParam("imagenFile") MultipartFile imagenFile, Model model) {
        Result result = new Result();
        try {

            if (bindingResult.hasErrors()) {
                model.addAttribute("usuario", usuario);
                model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
                model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

                //guardo en variables los id
                int idPais = usuario.Direcciones.get(0).colonia.municipio.estado.pais.getIdPais();
                int idEstado = usuario.Direcciones.get(0).colonia.municipio.estado.getIdEstado();
                int idMunicipio = usuario.Direcciones.get(0).colonia.municipio.getIdMunicipio();
                int idColonia = usuario.Direcciones.get(0).colonia.getIdColonia();

                if (idEstado != 0) {
                    //guardo el valor
                    model.addAttribute("estados", estadoDAOImplementation.GetAll(idPais).objects);
                    if (idMunicipio != 0) {
                        //guardo el valor
                        model.addAttribute("municipios", municipioDAOImplementation.GetAll(idEstado).objects);
                        if (idColonia != 0) {
                            //guardo el valor
                            model.addAttribute("colonias", coloniaDAOImplementation.GetAll(idMunicipio).objects);

                        }
                    }

                }
                return "form";
            }

            //imagen
            String nombreArchivo = imagenFile.getOriginalFilename();
            System.out.println(nombreArchivo);
            String[] cadena = nombreArchivo.split("\\.");
            if (cadena[1].equals("jpg") || cadena[1].equals("png")) {
                //convierto imagen a base 64, y la cargo en el modelo alumno 
                System.out.println("Imagen");

                //Leer bytes de la imagen
                try {
                    byte[] fileContent = imagenFile.getBytes();

                    //Codificar a Base64
                    String encodedString = Base64.getEncoder().encodeToString(fileContent);

                    usuario.setImagen(encodedString);

                    System.out.println(encodedString);

                } catch (Exception e) {
                    result.correct = true;
                    result.errorMessage = e.getLocalizedMessage();
                    result.ex = e;
                }

            } else if (imagenFile != null) {
                return "form";
            }

            //proceso de agregar datos y retorno a vista de todos los usuarios
            model.addAttribute("usuario", usuario);
            result = usuarioDAOImplementation.Add(usuario);
            if (result.correct == false) {
                return "form";
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return "redirect:/usuario";
    }

    @GetMapping("/delete/{IdUsuario}")
    public String DeteleUsuario(@PathVariable("IdUsuario") int identificador, RedirectAttributes redirectAttributes) {
        Result result = new Result();

        result = usuarioDAOImplementation.DeteleUsuario(identificador);
        if (result.correct == true) {
            System.out.println("Borrado con exito");
            redirectAttributes.addFlashAttribute("mensaje", "¡Producto creado exitosamente!");
            return "redirect:/usuario";
        } else {
            System.out.println("NO fue borrado");
            return "redirect:/usuario";
        }

    }

    @PostMapping("") //a donde lo dirigo?
    public String UpdateUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model model) {
        Result result = new Result();

        try {

            if (bindingResult.hasErrors()) {
                model.addAttribute("usuario", usuario);
                model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
                model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

                //guardo en variables los id
                int idPais = usuario.Direcciones.get(0).colonia.municipio.estado.pais.getIdPais();
                int idEstado = usuario.Direcciones.get(0).colonia.municipio.estado.getIdEstado();
                int idMunicipio = usuario.Direcciones.get(0).colonia.municipio.getIdMunicipio();
                int idColonia = usuario.Direcciones.get(0).colonia.getIdColonia();

                if (idEstado != 0) {
                    //guardo el valor
                    model.addAttribute("estados", estadoDAOImplementation.GetAll(idPais).objects);
                    if (idMunicipio != 0) {
                        //guardo el valor
                        model.addAttribute("municipios", municipioDAOImplementation.GetAll(idEstado).objects);
                        if (idColonia != 0) {
                            //guardo el valor
                            model.addAttribute("colonias", coloniaDAOImplementation.GetAll(idMunicipio).objects);

                        }
                    }

                }
                return "usuario";
            }

            result = usuarioDAOImplementation.UpdateUsuario(usuario);
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return "usuario";
    }

    @GetMapping("/GetById/IdUsuario")
    public Result GetById(@PathVariable("IdUsuario") int identificador, @ModelAttribute("usuario") Usuario usuario, Model model) {
        Result result = new Result();
        try {
            model.addAttribute("usuarios", result.objects);
            model.addAttribute("usuario", new Usuario());

            result = usuarioDAOImplementation.GetById(identificador);
            System.out.println("Funciona GetByIDPais");
            model.addAttribute("usuario", result.object);
            System.out.println("Funciona GetByIDPais");
            
        } catch (Exception e) {
            result.correct = false;
        }
        System.out.println("Funciona GetByIDPais");
        return result;
    }

    @GetMapping("getEstadosByPais/{IdPais}")
    @ResponseBody //retorna un json y no una vista
    public Result GetEstadosByIdPais(@PathVariable("IdPais") int IdPais) {

        Result result = estadoDAOImplementation.GetAll(IdPais);

        result.correct = true;
        return result;
    }

    @GetMapping("getMunicipioByEstado/{IdEstado}")
    @ResponseBody //
    public Result GetMunicipioByEstado(@PathVariable("IdEstado") int IdEstado) {

        Result result = municipioDAOImplementation.GetAll(IdEstado);

        result.correct = true;
        return result;
    }

    @GetMapping("getColoniaByMunicipio/{IdMunicipio}")
    @ResponseBody //
    public Result GetColoniaByMunicipio(@PathVariable("IdMunicipio") int IdMunicipio) {

        Result result = coloniaDAOImplementation.GetAll(IdMunicipio);

        result.correct = true;
        return result;
    }

}
