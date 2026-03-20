/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario;

/**
 *
 * @author artur
 */
public interface IUsuarioJPA {
    
    Result GetAll();
    Result Add(Usuario usuario);
    Result GetById(int identificador);
    Result DeleteUsuario (int identificador);
    Result AddDireccion (Direccion direccion, int identificador);
    Result DeleteDireccion (int identificador);
    Result GetByIdDireccion(int identificador);
    Result UpdateDireccion (Direccion direccion, int identificador);
    Result UpdateUsuario (Usuario usuario);
    Result UpdateEstatus (int identificador, int estatus);
    Result GetByUserName (String username);
}
