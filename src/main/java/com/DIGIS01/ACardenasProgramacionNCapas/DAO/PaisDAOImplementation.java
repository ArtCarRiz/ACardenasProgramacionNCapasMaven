/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.ML.Pais;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.sql.SQLType;
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
public class PaisDAOImplementation implements IPais {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {

        Result result = new Result();

        try {

            jdbcTemplate.execute("{CALL PAISGETALL(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resulset = (ResultSet) callableStatement.getObject(1);

                result.objects = new ArrayList<>();
                
                while (resulset.next()) {
                    Pais pais = new Pais();
                    result.objects.add(pais);
                    
                    pais.setIdPais(resulset.getInt("IdPais"));
                    pais.setNombre(resulset.getString("Nombre"));
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
