/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Colonia;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Rol;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion;
import jakarta.persistence.Query;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.Mapping;

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

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuario = queryUsuario.getResultList();

            ModelMapper modelMapper = new ModelMapper();
            Type listType = new TypeToken<List<com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario>>() {
            }.getType();

            List<com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario> listaML = modelMapper.map(usuario, listType);

//            List<com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario> listaML = usuario.stream().map(entity -> {
//                com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario ml = new com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario();
//                ml.setIdUsuario(entity.getIdUsuario());
//                ml.setNombre(entity.getNombre());
//                ml.setApellidoPaterno(entity.getApellidoPaterno());
//                ml.setApellidoMaterno(entity.getApellidoMaterno());
//                ml.setEmail(entity.getEmail());
//                ml.setCelular(entity.getCelular());
//                ml.setTelefono(entity.getTelefono());
//                ml.setCurp(entity.getCurp());
//                ml.setEstatus(entity.getEstatus());
//                ml.setSexo(entity.getSexo());
//                ml.setUsername(entity.getUsername());
//                ml.setFechaNacimiento(entity.getFechaNacimiento());
//                ml.setImagen(entity.getImagen());
//                
////                 Mapeo de Rol (Navegación muchos a one)
//                if (entity.getRol() != null) {
//                    ml.Rol = new com.DIGIS01.ACardenasProgramacionNCapas.ML.Rol();
//                    ml.Rol.setIdRol(entity.getRol().getIdRol());
//                    ml.Rol.setNombreRol(entity.getRol().getNombreRol());
//                }
//                
//                if (entity.getDirecciones() != null) {
//                    ml.Direcciones = new com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario().Direcciones;
//                    ml.Direcciones.add(entity.getDirecciones());
//                }
//
//                return ml;
//            }).toList();
            result.objects = (List<Object>) (Object) usuario;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Override
    @Transactional
    public Result Add(com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario usuario) {
        Result result = new Result();

        try {

            Usuario usuariojpa = new Usuario();

            usuariojpa.setNombre(usuario.getNombre());
            usuariojpa.setApellidoPaterno(usuario.getApellidoPaterno());
            usuariojpa.setApellidoMaterno(usuario.getApellidoMaterno());
            usuariojpa.setFechaNacimiento(usuario.getFechaNacimiento());
            usuariojpa.setTelefono(usuario.getTelefono());
            usuariojpa.setEmail(usuario.getEmail());
            usuariojpa.setUsername(usuario.getUsername());
            usuariojpa.setPassword(usuario.getPassword());
            usuariojpa.setSexo(usuario.getSexo());
            usuariojpa.setCelular(usuario.getCelular());
            usuariojpa.setCurp(usuario.getCurp());
            usuariojpa.Rol = new Rol();
            usuariojpa.Rol.setIdRol(usuario.Rol.getIdRol());
            usuariojpa.setImagen(usuario.getImagen());

            usuariojpa.Direcciones = new ArrayList<>();
            Direccion direccionjpa = new Direccion();
            direccionjpa.colonia = new Colonia();

            com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion direccion = usuario.Direcciones.get(0);

            direccionjpa.setCalle(direccion.getCalle());
            direccionjpa.setNumeroExterior(direccion.getNumeroExterior());
            direccionjpa.setNumeroInterior(direccion.getNumeroInterior());
            direccionjpa.colonia.setIdColonia(direccion.colonia.getIdColonia());

            usuariojpa.Direcciones.add(direccionjpa);
            direccionjpa.usuario = usuariojpa;

            entityManager.persist(usuariojpa);

            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result GetById(int identificador) {
        Result result = new Result();

        try { //usa find.
//            String consulta = "SELECT u FROM Usuario u";
//            TypedQuery<Usuario> query = entityManager.createQuery(consulta, Usuario.class);
//            query.setParameter("u", identificador);
//            result.object = query.getSingleResult();

            Usuario usuarioEntity = entityManager.find(Usuario.class, identificador);

            if (usuarioEntity != null) {

                ModelMapper modelMapper = new ModelMapper();
                com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario usuarioML = modelMapper.map(usuarioEntity, 
                        com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario.class);
                result.object = usuarioML;
            } else {
                result.correct = false;
            }

            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result DeleteUsuario(int identificador) {
        Result result = new Result();

        try {

//            String jpql = "DELETE FROM Usuario e WHERE e.IdUsuario = :IdUsuario";
//            Query query = entityManager.createQuery(jpql);
//            query.setParameter("IdUsuario", identificador);
//
//            int eliminados = query.executeUpdate(); 
////            entityManager.getTransaction().commit();
            com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario usuarioEntity = entityManager.find(com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario.class, identificador);

            entityManager.remove(usuarioEntity);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result AddDireccion(com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion direccion, int identificador) {
        Result result = new Result();

        try {

            ModelMapper modelMapper = new ModelMapper();
            com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion direccionjpa = modelMapper.map(direccion, com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion.class);

            Usuario usuariojpa = new Usuario();
            usuariojpa.setIdUsuario(identificador);
            direccionjpa.setUsuario(usuariojpa);

            entityManager.persist(direccionjpa);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result DeleteDireccion(int identificador) {
        Result result = new Result();

        try {

            com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion direccionEntity = entityManager.find(com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion.class, identificador);

            entityManager.remove(direccionEntity);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result GetByIdDireccion(int identificador) {
        Result result = new Result();

        try {

            com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion direccionEntity = entityManager.find(com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion.class, identificador);

            ModelMapper modelMapper = new ModelMapper();
            com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion direccionML = modelMapper.map(direccionEntity, com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion.class);
            result.object = direccionML;

            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateDireccion(com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion direccionML, int identificador) {
        Result result = new Result();

        try {
            
            Direccion direccionAntigua = entityManager.find(Direccion.class, identificador);
            
            direccionAntigua.setCalle(direccionML.getCalle());
            direccionAntigua.setNumeroInterior(direccionML.getNumeroInterior());
            direccionAntigua.setNumeroExterior(direccionML.getNumeroExterior());
            direccionAntigua.colonia = new Colonia();
            direccionAntigua.colonia.setIdColonia(direccionML.getColonia().getIdColonia());
            
            
            
            //sin esto va a querer hacer insert en ves de update
//            direccionAntigua.setIdDireccion(identificador);

            //
            entityManager.merge(direccionAntigua);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateUsuario(com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario usuario) {
        Result result = new Result();

        try {

            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());
            if (usuario != null) { // alumno si existe
                //ML -> JPA
                ModelMapper modelMapper = new ModelMapper();
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario usuarioJpa = modelMapper.map(usuario, com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario.class);
                
                usuarioJpa.Direcciones = usuarioBD.Direcciones;
                entityManager.merge(usuarioJpa);
                result.correct = true;

            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateEstatus(int identificador, int estatus) {
        Result result = new Result();

        try {
            
            Usuario usuariojpa = entityManager.find(Usuario.class, identificador);
            usuariojpa.setIdUsuario(identificador);
            usuariojpa.setEstatus(estatus);
            
            entityManager.merge(usuariojpa);
            result.correct = true;
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

}
