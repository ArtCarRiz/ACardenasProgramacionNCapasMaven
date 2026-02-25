/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

public class ValidationService {

    @Autowired
    private Validator validator;

    public BindingResult ValidateObject(Object object) {
        DataBinder dataBinder = new DataBinder(object);
        dataBinder.setValidator(validator);
        dataBinder.validate();

        return dataBinder.getBindingResult();
    }

}
