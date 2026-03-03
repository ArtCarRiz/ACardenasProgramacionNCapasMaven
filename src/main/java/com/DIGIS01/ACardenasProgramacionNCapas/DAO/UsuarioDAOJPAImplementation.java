/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;


/**
 *
 * @author artur
 */
@Repository
public class UsuarioDAOJPAImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager; //jpa

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            TypedQuery<Usuario> queryAlumno = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuario = queryAlumno.getResultList();

            // mapper ...
            List<com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario> listaML = usuario.stream().map(entity -> {
                com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario ml = new com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario();
                ml.setIdUsuario(entity.getIdUsuario());
                ml.setNombre(entity.getNombre());
                ml.setApellidoPaterno(entity.getApellidoPaterno());
                ml.setApellidoMaterno(entity.getApellidoMaterno());
                ml.setEmail(entity.getEmail());
                ml.setCelular(entity.getCelular());
                ml.setTelefono(entity.getTelefono());
                ml.setCurp(entity.getCurp());
                ml.setEstatus(entity.getEstatus());
                
//                 Mapeo de Rol (Navegación ManyToOne)
                if (entity.getRol() != null) {
                    ml.Rol = new com.DIGIS01.ACardenasProgramacionNCapas.ML.Rol();
                    ml.Rol.setIdRol(entity.getRol().getIdRol());
                    ml.Rol.setNombreRol(entity.getRol().getNombreRol());
                }

                return ml;
            }).toList();

            result.objects = (List<Object>) (Object) listaML;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

}
