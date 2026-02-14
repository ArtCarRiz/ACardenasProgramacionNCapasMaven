/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;

public interface IUsuario {
    
    Result GetAll();
    Result GetById(int identificador);
}
