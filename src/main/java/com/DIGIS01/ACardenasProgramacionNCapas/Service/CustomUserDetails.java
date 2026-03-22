/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

/**
 *
 * @author artur
 */
public class CustomUserDetails extends User {

    private final int idUsuario; //id de la bd 

    public CustomUserDetails(String username, String password, boolean enabled, Collection <? extends GrantedAuthority> authorities, int idUsuario) {
        super(username, password, enabled, true, true, true, authorities);
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

}
