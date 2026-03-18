/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.Service;

import com.DIGIS01.ACardenasProgramacionNCapas.DAO.UsuarioDAOJPAImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author digis
 */
@Service
public class UserDetailJPA implements UserDetailsService {

    private final UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    public UserDetailJPA(UsuarioDAOJPAImplementation usuarioDAOJPAImplementation) {
        this.usuarioDAOJPAImplementation = usuarioDAOJPAImplementation;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result result = new Result();
        
        result = usuarioDAOJPAImplementation.GetByEmail(username);
        
        Usuario usuario = (Usuario) result.object;
        return User.withUsername(usuario.getNombre())
                .password(usuario.getPassword())
                .roles(usuario.Rol.getNombreRol())
                .disabled((usuario.getEstatus() == 0) ? true : false) 
                .build();
    }

}
