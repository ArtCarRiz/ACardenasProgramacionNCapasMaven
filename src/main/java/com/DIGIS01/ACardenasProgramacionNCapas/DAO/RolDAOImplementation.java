/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.ML.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.ML.Rol;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RolDAOImplementation implements IRol{
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll(){
        Result result = new Result();
        
        try {
            
            jdbcTemplate.execute("{CALL ROLGETALLSP (?)}", (CallableStatementCallback<Boolean>) callablestatement ->{
                
                callablestatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callablestatement.execute();
                
                ResultSet resultSet = (ResultSet) callablestatement.getObject(1);
                
                result.objects = new ArrayList<>();
                
                while (resultSet.next()) {
                    Rol rol = new Rol();
                    result.objects.add(rol);
                    
                    rol.setIdRol(resultSet.getInt("IDROL"));
                    rol.setNombreRol(resultSet.getString("NOMBREROL"));
                    
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
