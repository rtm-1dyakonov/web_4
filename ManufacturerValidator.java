package com.validators;

import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("manufacturerValidator")
public class ManufacturerValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Pattern pattern = Pattern.compile("[A-Za-z\\.\\s]{3,}");
        if(!pattern.matcher(value.toString()).find()){
            FacesMessage msg = new FacesMessage("Некорректное значение производителя: допустимые символы:\n большие и маленькие латинские буквы, точка, пробел \n минимальное количество символов: 3");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
}
