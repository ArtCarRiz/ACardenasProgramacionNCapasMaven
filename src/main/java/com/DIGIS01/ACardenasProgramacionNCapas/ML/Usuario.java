/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {

    private int IdUsuario;
    @NotEmpty(message = "No puedo ser vacio")
    @Size(min = 3, max = 50, message = "más de 3 letras min")
    private String Nombre;
    @NotEmpty(message = "No puedo ser vacio")
    @Size(min = 3, max = 50, message = "más de 3 letras min")
    private String ApellidoPaterno;
    @NotEmpty(message = "No puedo ser vacio")
    @Size(min = 3, max = 50, message = "más de 3 letras min")
    private String ApellidoMaterno;

    @NotNull(message = "La fecha no puede ser nula")
    @PastOrPresent(message = "La fecha debe ser en el pasado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento; 
    
    @NotEmpty(message = "No puedo ser vacio")
    @Pattern(regexp = "^[0-9]{10}$", message = "Solo numeros")
    @Size(min = 10, max = 10, message = "Debe haber minimo 10 numeros")
    private String Telefono;
    
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z]+.com$", message = "formato invalido")
    @NotEmpty(message = "No puedo ser vacio")
    private String Email;
    
    @NotEmpty(message = "No puedo ser vacio")
    @Pattern(regexp = "^[a-zA-Z ]+[0-9]+$", message = "min una letra y min un numero")
    private String Username;
    
    @NotEmpty(message = "No puedo ser vacio")
    private String Password;
    
    @NotEmpty(message = "No puedo ser vacio")
    private String Sexo;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "Solo numeros")
    @Size(min = 10, max = 10, message = "Debe haber minimo 10 numeros")
    private String Celular;
    
    @Pattern(regexp = "^([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$", message = "Formato invalido")
    private String Curp;
    
    public String Imagen;

//    @Valid
//    @NotEmpty(message = "No puede estar vacio")
    public List<com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion> Direcciones;
    
    @Valid
    public com.DIGIS01.ACardenasProgramacionNCapas.ML.Rol Rol;

    public Usuario() {
    }

    public Usuario(String Nombre, String ApellidoPaterno, String ApellidoMaterno, Date FechaNacimiento, String Telefono, String Email, String Username, String Password, String Sexo, String Celular, String Curp, String Imagen) {
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.FechaNacimiento = FechaNacimiento;
        this.Telefono = Telefono;
        this.Email = Email;
        this.Username = Username;
        this.Password = Password;
        this.Sexo = Sexo;
        this.Celular = Celular;
        this.Curp = Curp;
        this.Imagen = Imagen;
    }

//    public List<ML.Direccion> Direcciones;
    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEmail() {
        return Email;
    }

    public void setUsername(String UserName) {
        this.Username = UserName;
    }

    public String getUsername() {
        return Username;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPassword() {
        return Password;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCurp(String Curp) {
        this.Curp = Curp;
    }

    public String getCurp() {
        return Curp;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }
    

    public void setRol(Rol rol) {
        this.Rol = rol;
    }

    public Rol getRol() {
        return Rol;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }
    
    

}
