/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.Service;

import com.DIGIS01.ACardenasProgramacionNCapas.DAO.UsuarioDAOJPAImplementation;

/**
 *
 * @author artur
 */
public class UserDetailJPA {

    private final UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    public UserDetailJPA(UsuarioDAOJPAImplementation usuarioDAOJPAImplementation) {
        this.usuarioDAOJPAImplementation = usuarioDAOJPAImplementation;
    }

//    @Override // recuperar la información de la bd / y gestionar que rol, que permisos tiene en el sistema
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Result result = alumnoJPADAOImplementation.GetByEmail(username);
//
//        /*
//        UserDetails
//            - password coincide
//            - status 0 - no da acceso
//            - rol - indica o almacena para gestionar niveles de permiso
//            - 
//         */
//        Alumno alumno = (Alumno) result.object;
//        return User.withUsername(alumno.getNombre())
//                .password(alumno.getPassword())
//                .disabled(false) // (alumno.getStatus() == 0) ? true : false
//                .build();
//    }

}
