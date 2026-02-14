/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author digis
 */
@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource datasource(){
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        
        datasource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        datasource.setUsername("ACardenasProgramacionNCapas");
        datasource.setPassword("password1");
        
        return datasource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate (DataSource datasource){
        return new JdbcTemplate(datasource); //checa este return
    }
    
    
}
