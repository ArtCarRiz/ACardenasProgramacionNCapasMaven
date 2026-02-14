/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.ML;

/**
 *
 * @author digis
 */
public class Municipio {

    private int IdMunicipio;
    private String Nombre;
    public com.DIGIS01.ACardenasProgramacionNCapas.ML.Estado estado;
//    public ML.Estado estado;

    public void setIdMunicipio(int IdMunicipio) {
        this.IdMunicipio = IdMunicipio;
    }

    public int getIdMunicipio() {
        return IdMunicipio;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNombre() {
        return Nombre;
    }

}
