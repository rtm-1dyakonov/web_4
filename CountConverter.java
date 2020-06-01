package com.converters;

import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;

@FacesConverter("countConverter")
public class CountConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Pattern pattern = Pattern.compile("[1-9][0-9]*");
        if (!pattern.matcher(value.toString()).find()) {
            FacesMessage msg = new FacesMessage("Количество должно быть целым положительным числом");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
            
        } 
        return Integer.parseInt(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(value);
    }
    
}
