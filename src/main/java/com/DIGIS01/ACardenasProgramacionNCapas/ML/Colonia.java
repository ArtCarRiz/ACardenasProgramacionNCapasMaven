/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.ML;

/**
 *
 * @author digis
 */
public class Colonia {

    private int IdColonia;
    private String Nombre;
    private String CodigoPostal;
    public com.DIGIS01.ACardenasProgramacionNCapas.ML.Municipio municipio;
//    public ML.Municipio municipio;

    public Colonia() {
    }

    
    
    public Colonia(int IdColonia, String Nombre, String CodigoPostal, Municipio municipio) {
        this.IdColonia = IdColonia;
        this.Nombre = Nombre;
        this.CodigoPostal = CodigoPostal;
        this.municipio = municipio;
    }
    
    

    public void setIdColonia(int IdColonia) {
        this.IdColonia = IdColonia;
    }

    public int getIdColonia() {
        return IdColonia;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

}
