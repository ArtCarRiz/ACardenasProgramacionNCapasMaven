/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import org.springframework.stereotype.Repository;
import com.DIGIS01.ACardenasProgramacionNCapas.Controller.UsuarioController;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Colonia;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Direccion;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Estado;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Municipio;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Pais;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Rol;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Usuario;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.transaction.annotation.Transactional;

@Repository // logica de base de datos.
public class UsuarioDAOImplementation implements IUsuario {

    @Autowired // inyecta una conexión a la base de datos.
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            jdbcTemplate.execute("{CALL USUARIODIRECCIONGETALLSP(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.objects = new ArrayList<>();
                while (resultSet.next()) {
                    int idUsuario = resultSet.getInt("IdUsuario");
                    if (!result.objects.isEmpty() && idUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {

                        Direccion direccion = new Direccion();

                        ///////////////////////////////
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior("NumeroInterior");
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.colonia = new Colonia();
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.pais = new Pais();

                        direccion.colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.colonia.municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.colonia.municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.colonia.municipio.estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.colonia.municipio.estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.colonia.municipio.estado.pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.colonia.municipio.estado.pais.setNombre("NombrePais");

                        /////////////////////////////////
                        ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                    } else {
                        Usuario usuario = new Usuario();
                        usuario.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();

                        usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                        usuario.setNombre(resultSet.getString("NOMBREUSUARIO"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        //los datos que faltan
                        usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario.setCelular(resultSet.getString("Celular"));
                        usuario.setUsername(resultSet.getString("UserName"));
                        usuario.setSexo(resultSet.getString("Sexo"));
                        usuario.setCurp(resultSet.getString("Curp"));

                        usuario.setImagen(resultSet.getString("Imagen"));
                        usuario.Rol = new Rol();
                        usuario.Rol.setIdRol(resultSet.getInt("IDROL"));
                        usuario.Rol.setNombreRol(resultSet.getString("NOMBREROL"));

                        result.objects.add(usuario);

                        int idDireccion = resultSet.getInt("IdDireccion");
                        if (idDireccion != 0) {
                            usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                            usuario.setNombre(resultSet.getString("NOMBREUSUARIO"));
                            usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                            usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                            usuario.setEmail(resultSet.getString("Email"));
                            usuario.setTelefono(resultSet.getString("Telefono"));

                            ///////////////////////////////
                            direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior("NumeroInterior");
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                            direccion.colonia = new Colonia();
                            direccion.colonia.municipio = new Municipio();
                            direccion.colonia.municipio.estado = new Estado();
                            direccion.colonia.municipio.estado.pais = new Pais();

                            direccion.colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            direccion.colonia.municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.colonia.municipio.setNombre(resultSet.getString("NombreMunicipio"));
                            direccion.colonia.municipio.estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.colonia.municipio.estado.setNombre(resultSet.getString("NombreEstado"));
                            direccion.colonia.municipio.estado.pais.setIdPais(resultSet.getInt("IdPais"));
                            direccion.colonia.municipio.estado.pais.setNombre("NombrePais");

                            /////////////////////////////////
                            ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                        }

                    }
                }

                return true;
            });

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result GetById(int identificador) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL GETALLBYIDDIRECCION(?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, identificador);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                /////////
//                if (resultSet.next()) {
                result.objects = new ArrayList<>();
                while (resultSet.next()) {
                    int idUsuario = resultSet.getInt("IdUsuario");
                    //si no esta vacias yyy es el mismo que el ultimo
                    if (!result.objects.isEmpty() && idUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {
                        Direccion direccion = new Direccion();

                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior("NumeroInterior");
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.colonia = new Colonia();
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.pais = new Pais();

                        direccion.colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.colonia.municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.colonia.municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.colonia.municipio.estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.colonia.municipio.estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.colonia.municipio.estado.pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.colonia.municipio.estado.pais.setNombre("NombrePais");

                        ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                    } else {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(idUsuario);
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setCelular(resultSet.getString("Celular"));
                        usuario.setUsername(resultSet.getString("Username"));
                        usuario.setPassword(resultSet.getString("Password"));
                        usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario.setCurp(resultSet.getString("Curp"));
                        usuario.setImagen(resultSet.getString("Imagen"));
                        usuario.setSexo(resultSet.getString("Sexo"));
                        
                        usuario.Rol = new Rol();
                        usuario.Rol.setIdRol(resultSet.getInt("IdRol"));

                        int idDireccion = resultSet.getInt("IdDireccion");
                        if (idDireccion != 0) {
                            usuario.Direcciones = new ArrayList<>();
                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(idDireccion);
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                            direccion.colonia = new Colonia();
                            direccion.colonia.municipio = new Municipio();
                            direccion.colonia.municipio.estado = new Estado();
                            direccion.colonia.municipio.estado.pais = new Pais();

                            direccion.colonia.setIdColonia(resultSet.getInt("idcolonia"));
                            direccion.colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            direccion.colonia.municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.colonia.municipio.setNombre(resultSet.getString("NombreMunicipio"));
                            direccion.colonia.municipio.estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.colonia.municipio.estado.setNombre(resultSet.getString("NombreEstado"));
                            direccion.colonia.municipio.estado.pais.setIdPais(resultSet.getInt("IdPais"));
                            direccion.colonia.municipio.estado.pais.setNombre("NombrePais");

                            usuario.Direcciones.add(direccion);
//                            ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                        }
                        result.object = usuario;
//                        result.objects.add(usuario);
                        result.correct = true;
                    }
                }

                //////
                return true;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result Add(Usuario usuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionAddSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
//            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);            
                Direccion direccion = usuario.Direcciones.get(0);
//            direccion.setCalle(resultSet.getString("Calle")); //ya no se usa el resultset

                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());

                callableStatement.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                System.out.println(usuario.getFechaNacimiento());
                System.out.println(usuario.getFechaNacimiento().getTime());

                callableStatement.setString(5, usuario.getTelefono());
                callableStatement.setString(6, usuario.getEmail());
                callableStatement.setString(7, usuario.getUsername());
                callableStatement.setString(8, usuario.getPassword());
                callableStatement.setString(9, usuario.getSexo());
                callableStatement.setString(10, usuario.getCelular());
                callableStatement.setString(11, usuario.getCurp());
                callableStatement.setInt(12, usuario.Rol.getIdRol());
                callableStatement.setString(13, usuario.getImagen());
                //aqui empiezan las direcciones
                callableStatement.setString(14, direccion.getCalle());
                callableStatement.setString(15, direccion.getNumeroInterior());
                callableStatement.setString(16, direccion.getNumeroExterior());
                callableStatement.setInt(17, direccion.colonia.getIdColonia());

                int rowAffected = 0;
                rowAffected = callableStatement.executeUpdate();

                result.correct = rowAffected != 0 ? true : false;

                return true;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result DeteleUsuario(int identificador) {
        Result result = new Result();

        try {

            jdbcTemplate.execute("{CALL UsuarioDelete(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setInt(1, identificador);
                int rowAffect = callableStatement.executeUpdate();

                if (rowAffect != 0) {
                    result.correct = true;
                } else {
                    System.out.println("El delete no se concreto");
                    result.correct = false;
                }

                return true;
            });

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result UpdateUsuario(Usuario usuario) {
        Result result = new Result();

        try {

            jdbcTemplate.execute("{CALL usuarioupdate(?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(5, usuario.getTelefono());
                callableStatement.setString(6, usuario.getEmail());
                callableStatement.setString(7, usuario.getUsername());
                callableStatement.setString(8, usuario.getPassword());
                callableStatement.setString(9, usuario.getSexo());
                callableStatement.setString(10, usuario.getCelular());
                callableStatement.setString(11, usuario.getCurp());
                callableStatement.setInt(12, usuario.Rol.getIdRol());
                callableStatement.setInt(13, usuario.getIdUsuario());
                
                int rowAffectted = 0;
                rowAffectted = callableStatement.executeUpdate();
                
                result.correct = rowAffectted != 0 ? true : false;

                return true;
            });

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

}
