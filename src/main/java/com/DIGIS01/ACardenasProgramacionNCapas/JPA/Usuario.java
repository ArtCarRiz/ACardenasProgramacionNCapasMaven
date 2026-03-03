/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Usuario {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int IdUsuario;
    @Column (name = "nombre")
    private String Nombre;
    @Column (name = "apellidopaterno")
    private String ApellidoPaterno;
    @Column (name = "apellidomaterno")
    private String ApellidoMaterno;

//    @NotNull(message = "La fecha no puede ser nula")
//    @PastOrPresent(message = "La fecha debe ser en el pasado")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date FechaNacimiento; 
    @Column (name = "telefono")
    private String Telefono;

    @Column (name = "email")
    private String Email;
    
    @Column (name = "username")
    private String Username;
    
    @Column (name = "password")
    private String Password;

    @Column (name = "sexo")
    private String Sexo;

    @Column (name = "celular")
    private String Celular;
    
    @Column (name = "curp")
    private String Curp;

//    @Valid
//    public String Imagen;
    @Column (name = "estatus")
    public int Estatus;

//    @Valid
//    @NotEmpty(message = "No puede estar vacio")
//    public List<com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion> Direcciones;
//    
    @ManyToOne
    @JoinColumn(name = "idrol") // FK
    public com.DIGIS01.ACardenasProgramacionNCapas.JPA.Rol Rol;
    
    public Usuario() {
    }

//    public Usuario(String Nombre, String ApellidoPaterno, String ApellidoMaterno, Date FechaNacimiento, String Telefono, String Email, String Username, String Password, String Sexo, String Celular, String Curp, String Imagen, int Estatus) {
//        this.Nombre = Nombre;
//        this.ApellidoPaterno = ApellidoPaterno;
//        this.ApellidoMaterno = ApellidoMaterno;
//        this.FechaNacimiento = FechaNacimiento;
//        this.Telefono = Telefono;
//        this.Email = Email;
//        this.Username = Username;
//        this.Password = Password;
//        this.Sexo = Sexo;
//        this.Celular = Celular;
//        this.Curp = Curp;
//        this.Imagen = Imagen;
//        this.Estatus = Estatus;
//    }
//    public List<ML.Direccion> Direcciones;
    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int Estatus) {
        this.Estatus = Estatus;
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

//    public void setFechaNacimiento(Date FechaNacimiento) {
//        this.FechaNacimiento = FechaNacimiento;
//    }
//
//    public Date getFechaNacimiento() {
//        return FechaNacimiento;
//    }
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

//    public String getImagen() {
//        return Imagen;
//    }
//
//    public void setImagen(String Imagen) {
//        this.Imagen = Imagen;
//    }
//    
//
    public void setRol(Rol rol) {
        this.Rol = rol;
    }

    public Rol getRol() {
        return Rol;
    }
//
//    public List<Direccion> getDirecciones() {
//        return Direcciones;
//    }
//
//    public void setDirecciones(List<Direccion> Direcciones) {
//        this.Direcciones = Direcciones;
//    }
}
