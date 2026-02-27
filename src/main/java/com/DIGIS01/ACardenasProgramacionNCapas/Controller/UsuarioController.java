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
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Colonia;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.ErroresArchivo;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Estado;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Rol;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario;
import com.DIGIS01.ACardenasProgramacionNCapas.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
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
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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

    @Autowired
    private ValidationService validationService;

    @GetMapping
    public String Index(Model model) {
        Result result = usuarioDAOImplementation.GetAll();

        model.addAttribute("usuarios", result.objects);
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
//        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

        model.addAttribute("usuariobusqueda", new Usuario());

        return "usuario";

    }

    @PostMapping
    public String Index(@ModelAttribute("usuariobusqueda") Usuario usuario, Model model) {
        Result result = new Result();
        try {
            result = usuarioDAOImplementation.BusquedaLibre(usuario);
            model.addAttribute("usuarios", result.objects);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

            model.addAttribute("usuariobusqueda", usuario);
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return "usuario";
    }

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

    @PostMapping("/addDireccion")
    public String AddDireccion(@ModelAttribute("direccion") Direccion direccion, @RequestParam("idUsuarioRelacion") int idUsuario, RedirectAttributes redirectAttributes) {
        Result result = new Result();

        try {
            result = usuarioDAOImplementation.AddDireccion(direccion, idUsuario);

            if (result.correct) {
                redirectAttributes.addFlashAttribute("mensaje", "Dirección agregada correctamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error al agregar dirección: " + result.errorMessage);
            }

        } catch (Exception e) {
        }

        return "redirect:/usuario/details/" + idUsuario;
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

    @PostMapping("/delete/{IdUsuario}/{IdDireccion}")
    public String DeleteDireccion(@PathVariable("IdUsuario") int identificador, @PathVariable("IdDireccion") int identificadorDireccion, RedirectAttributes redirectAttributes) {
        Result result = new Result();

        result = usuarioDAOImplementation.DeleteDireccion(identificador, identificadorDireccion);

        if (result.correct == true) {
            System.out.println("Borrado con exito");
            redirectAttributes.addFlashAttribute("mensaje", "¡Producto creado exitosamente!");
            return "redirect:/usuario/details/" + identificador;
        } else {
            System.out.println("NO fue borrado");
            return "redirect:/usuario/details/" + identificador;
        }
    }

    @PostMapping("/update/{IdDireccion}/{IdUsuario}")
    public String UpdateDireccion(@ModelAttribute("direccion") Direccion direccion, @PathVariable("IdDireccion") int IdDireccion, @PathVariable("IdUsuario") int IdUsuario, RedirectAttributes redirectAttributes) {
        Result result = new Result();

        try {
            direccion.setIdDireccion(IdDireccion);

            result = usuarioDAOImplementation.UpdateDireccion(direccion);

            if (result.correct) {
                redirectAttributes.addFlashAttribute("mensaje", "Dirección actualizada correctamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error al actualizar: " + result.errorMessage);
            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return "redirect:/usuario/details/" + IdUsuario;
    }

    @PostMapping("/update/{IdUsuario}")
    public String UpdateUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, @PathVariable("IdUsuario") int identificador, @RequestParam("imagenFile") MultipartFile imagenFile, Model model) {
        Result result = new Result();
        try {
            //  Verificar si se subió un archivo nuevo
            if (imagenFile != null && !imagenFile.isEmpty()) {
                String nombreArchivo = imagenFile.getOriginalFilename();
                String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();

                if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
                    // Leer bytes y convertir a Base64
                    byte[] fileContent = imagenFile.getBytes();
                    String encodedString = Base64.getEncoder().encodeToString(fileContent);
                    usuario.setImagen(encodedString);
                }
            } else {

                result = usuarioDAOImplementation.GetById(identificador);
                if (result.correct) {
                    Usuario usuarioanterior = (Usuario) result.object;
                    usuario.setImagen(usuarioanterior.getImagen());
                }
            }

            // Ejecutar la actualización
            result = usuarioDAOImplementation.UpdateUsuario(usuario);

            if (!result.correct) {
                return "redirect:/usuario/details/" + identificador;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "redirect:/usuario/details/" + identificador;
    }

    @GetMapping("/GetById/{IdUsuario}")
    @ResponseBody
    public Result GetById(@PathVariable("IdUsuario") int identificador, Model model) {
        Result result = new Result();
        try {
            result = usuarioDAOImplementation.GetById(identificador);

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        System.out.println("Funciona GetByIDPais");
        return result;
    }

    @GetMapping("/GetByIdDireccion/{IdDireccion}")
    @ResponseBody
    public Result GetByIdDireccion(@PathVariable("IdDireccion") int identificador, Model model) {
        Result result = new Result();
        try {
            result = usuarioDAOImplementation.GetByIdDireccion(identificador);

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @GetMapping("/details/{IdUsuario}")
    public String Details(@PathVariable("IdUsuario") int identificador, Model model) {
        try {
            Result resultUsuario = usuarioDAOImplementation.GetById(identificador);

            if (resultUsuario.correct) {
                Usuario usuario = (Usuario) resultUsuario.object;
                model.addAttribute("usuario", usuario);
                model.addAttribute("direccion", new Direccion());

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

            }
        } catch (Exception e) {
            System.out.println("Error en details: " + e.getMessage());
        }
        return "details";
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

    @GetMapping("/cargaMasiva")
    public String CargaMasiva() {
        return "cargaMasiva";
    }

    @PostMapping("cargaMasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        Result result = new Result();
        List<Usuario> usuarios = new ArrayList<>();
        try {
            if (archivo != null) {

                String rutaBase = System.getProperty("user.dir");
                String rutaCarpeta = "src/main/resources/archivosCM";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String nombreArchivo = fecha + archivo.getOriginalFilename();
                String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
                String extension = archivo.getOriginalFilename().split("\\.")[1];

                File archivoFile = new File(rutaArchivo);

                if (extension.equals("txt")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoTxt(archivoFile);
                } else if (extension.equals("xlsx")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoXLSX(new File(rutaArchivo));
                } else {
                    System.out.println("Extensión erronea, manda archivos del formato solicitado");
                }

                List<ErroresArchivo> errores = ValidarDatos(usuarios);

                if (errores.isEmpty()) {
                    String uuid = UUID.randomUUID().toString();
                    String llaveSession = "ruta" + uuid;
                    session.setAttribute(llaveSession, rutaArchivo);
                    model.addAttribute("uuid", uuid);
                    model.addAttribute("errores", false);

//                    redirectAttributes.addFlashAttribute("mensaje", archivoFile);
//                    return "redirect:usuario";
                } else {
                    model.addAttribute("errores", errores);
                    model.addAttribute("funciono", true);

//                    return "cargaMasiva";
//                    retorno lista errores, y la renderizo.
                }

            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return "cargaMasiva";
    }

    @GetMapping("/cargaMasiva/procesar/{uuid}")
    public String ProcesarCargaMasiva(@PathVariable("uuid") String uuid, RedirectAttributes redirectAttributes, HttpSession session) {
        Result result = new Result();
        List<Usuario> usuarios = new ArrayList<>();

        String llaveSession = "ruta" + uuid;
        Object objetoRuta = session.getAttribute(llaveSession);

        if (objetoRuta == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el archivo o la sesión expiró.");
            return "redirect:/usuario/cargaMasiva";
        }

        String rutaReal = objetoRuta.toString();
        File archivoFile = new File(rutaReal);

        String extension = rutaReal.substring(rutaReal.lastIndexOf(".") + 1);
        if (extension.equals("txt")) {
            usuarios = LecturaArchivoTxt(archivoFile);
        } else {
            usuarios = LecturaArchivoXLSX(archivoFile);
        }

        result = usuarioDAOImplementation.AddAll(usuarios);

        if (result.correct) {
            redirectAttributes.addFlashAttribute("successMesage", "Carga masiva exitosa.");
            session.removeAttribute(llaveSession); // Limpiar sesión
        } else {
            redirectAttributes.addFlashAttribute("error", "Error: " + result.errorMessage);
        }

        return "redirect:/usuario";

        ////PRUEBA
//        String extension = FilenameUtils.getExtension(archivo.getName());
//        List<Usuario> usuarios = new ArrayList<>();
//
//        String nombreArchivo = archivo.getName();
//        File archivoFile = new File(nombreArchivo);
//
//        if (extension.equals("txt")) {
//            usuarios = LecturaArchivoTxt(archivo);
//        } else if (extension.equals("xlsx")) {
//            usuarios = LecturaArchivoXLSX(archivo);
//        } else {
//            System.out.println("Extensión erronea, manda archivos del formato solicitado");
//        }
//
//        for (Usuario usuario : usuarios) {
//            usuarioDAOImplementation.AddPrueba(usuario);
//        }
//
//        //FIN PRUEBA
//        System.out.println("El procesarCargaMasiva funciona");
//        System.out.println(archivo);
//        redirectAttributes.addFlashAttribute("successMesage", "El recurso se agrego de forma correcta");
//        return "redirect:/usuario";
    }

    public List<Usuario> LecturaArchivoTxt(File archivo) {
        Result result = new Result();
        List<Usuario> usuarios = null;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String linea;
            usuarios = new ArrayList<>();

            // br.readLine(); 
            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split("\\|");

                if (datos.length >= 17) {
                    Usuario usuario = new Usuario();
                    usuario.Rol = new Rol();
                    usuario.Direcciones = new ArrayList<>();

                    usuario.setNombre(datos[0].trim());
                    usuario.setApellidoPaterno(datos[1].trim());
                    usuario.setApellidoMaterno(datos[2].trim());
                    try {
                        String fecha = datos[3].trim();
                        System.out.println(fecha);
                        usuario.setFechaNacimiento(formatoFecha.parse(fecha));
                    } catch (Exception e) {
                        result.errorMessage = e.getLocalizedMessage();
                    }

                    System.out.println(usuario.getFechaNacimiento());

                    usuario.setTelefono(datos[4].trim());
                    usuario.setEmail(datos[5].trim());
                    usuario.setUsername(datos[6].trim());
                    usuario.setPassword(datos[7].trim());
                    usuario.setSexo(datos[8].trim());
                    usuario.setCelular(datos[9].trim());
                    usuario.setCurp(datos[10].trim());
                    try {
                        int idRol = Integer.parseInt(datos[11].trim());
                        usuario.Rol.setIdRol(idRol);
                    } catch (NumberFormatException e) {
                        usuario.Rol.setIdRol(0);
                    }
                    
                    usuario.setImagen(datos[12].trim());

                    //DIRECCION
                    Direccion direccion = new Direccion();
                    direccion.colonia = new Colonia();
                    direccion.setCalle(datos[13].trim());
                    direccion.setNumeroExterior(datos[14].trim());
                    direccion.setNumeroInterior(datos[15].trim());
                    direccion.colonia.setIdColonia(Integer.parseInt(datos[16].trim()));

                    usuario.Direcciones.add(direccion);
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return usuarios;
    }

    public List<Usuario> LecturaArchivoXLSX(File archivo) {
        List<Usuario> usuarios = null;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        try (InputStream inputStream = new FileInputStream(archivo); XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            XSSFSheet sheet = workbook.getSheetAt(0);

            usuarios = new ArrayList<>();

            for (Row row : sheet) {
                Usuario usuario = new Usuario();
                usuario.Rol = new Rol();

                usuario.setNombre(row.getCell(0).toString());
                usuario.setApellidoPaterno(row.getCell(1).toString());
                usuario.setApellidoMaterno(row.getCell(2).toString());
                usuario.setFechaNacimiento(row.getCell(3).getDateCellValue());

                usuario.setTelefono(row.getCell(4).toString());
                usuario.setEmail(row.getCell(5).toString());
                usuario.setUsername(row.getCell(6).toString());
                usuario.setPassword(row.getCell(7).toString());
                usuario.setSexo(row.getCell(8).toString());
                usuario.setCelular(row.getCell(9).toString());
                usuario.setCurp(row.getCell(10).toString());

                usuario.Rol.setIdRol((int) row.getCell(11).getNumericCellValue());

                usuarios.add(usuario);
            }

        } catch (Exception ex) {
        }

        return usuarios;
    }

    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios) {
        List<ErroresArchivo> errores = new ArrayList<>();
        int numeroFila = 1; // sin encabezados
        for (Usuario usuario : usuarios) {

            BindingResult bindingResult = validationService.ValidateObject(usuario);

            if (bindingResult.hasErrors()) {
                ErroresArchivo erroresArchivo = new ErroresArchivo();
                for (ObjectError objectError : bindingResult.getAllErrors()) {

//                    erroresArchivo.dato = objectError.getObjectName();
                    erroresArchivo.dato = ((FieldError) objectError).getField();
                    erroresArchivo.descripcion = objectError.getDefaultMessage();
                    erroresArchivo.fila = numeroFila;

                    errores.add(erroresArchivo);
                }

            }
            numeroFila++;
        }
        return errores;
    }

}
