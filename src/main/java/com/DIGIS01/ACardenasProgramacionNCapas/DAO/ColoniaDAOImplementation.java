/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.ML.Colonia;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author digis
 */
@Repository
public class ColoniaDAOImplementation implements IColonia {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll(int identificador) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL ColoniaByMunicipioPS (?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, identificador);
                callableStatement.execute();

                ResultSet resultset = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();

                while (resultset.next()) {
                    Colonia colonia = new Colonia();
                    result.objects.add(colonia);
                    
                    colonia.setIdColonia(resultset.getInt("IDCOLONIA"));
                    colonia.setNombre(resultset.getString("NOMBRE"));
                    colonia.setCodigoPostal(resultset.getString("CODIGOPOSTAL"));
                    
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

}
