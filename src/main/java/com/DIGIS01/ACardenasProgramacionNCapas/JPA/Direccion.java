/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.JPA;

import com.DIGIS01.ACardenasProgramacionNCapas.ML.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author digis
 */
@Entity
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private int IdDireccion;

    @Column(name = "calle")
    private String Calle;

    @Column(name = "numerointerior")
    private String NumeroInterior;
    @Column(name = "numeroexterior")
    private String NumeroExterior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    public Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idcolonia")
    public Colonia colonia;

    public Direccion() {

    }

    public Direccion(int IdDireccion, String Calle, String NumeroInterior, String NumeroExterior) {
        this.IdDireccion = IdDireccion;
        this.Calle = Calle;
        this.NumeroInterior = NumeroInterior;
        this.NumeroExterior = NumeroExterior;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getCalle() {
        return Calle;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

//    public Colonia getColonia() {
//        return colonia;
//    }
//
//    public void setColonia(Colonia colonia) {
//        this.colonia = colonia;
//    }
    public com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario usuario) {
        this.usuario = usuario;
    }

}
