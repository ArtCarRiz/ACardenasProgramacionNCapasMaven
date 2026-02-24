/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario;

public interface IUsuario {
    
    Result GetAll();
    Result GetById(int identificador);
    Result Add(Usuario usuario);
    Result DeteleUsuario (int identificador);
    Result UpdateUsuario(Usuario usuario);
    Result DeleteDireccion (int identificador, int identificadorDireccion);
    Result AddDireccion (Direccion direccion, int identificador);
    Result UpdateDireccion (Direccion direccion);
    Result GetByIdDireccion (int identificador);
    Result BusquedaLibre (Usuario usuario);
}
