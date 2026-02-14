/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

/**
 *
 * @author digis
 */
public class Rol {
    
    private int IdRol;
    @NotEmpty(message = "No puedo ser vacio")
    private String NombreRol;
    
    public void setIdRol(int IdRol){
        this.IdRol = IdRol;
    }
    
    public int getIdRol(){
        return IdRol;
    }
    
    public void setNombreRol(String NombreRol){
        this.NombreRol = NombreRol;
    }
    
    public String getNombreRol (){
        return NombreRol;
    }
    
}
