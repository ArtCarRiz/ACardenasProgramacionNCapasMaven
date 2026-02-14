/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.ML.Municipio;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *
 * @author digis
 */
@Repository
public class MunicipioDAOImplementation implements IMunicipio{
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll(int IdEstado){
        Result result = new Result();
        
        try {
            jdbcTemplate.execute("{CALL MunicipiosByEstadoSP (?, ?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
               
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdEstado);
                callableStatement.execute();
                
                
                ResultSet resultset =(ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();
                
                while (resultset.next()) {                    
                    Municipio municipio = new Municipio();
                    result.objects.add(municipio);
                    
                    municipio.setIdMunicipio(resultset.getInt("IDMUNICIPIO"));
                    municipio.setNombre(resultset.getString("NOMBRE"));
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
