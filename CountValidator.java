package com.validators;

import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("countValidator")
public class CountValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Pattern pattern = Pattern.compile("[1-9][0-9]*");
        if (!pattern.matcher(value.toString()).find()) {
            FacesMessage msg = new FacesMessage("Количество должно быть целым положительным числом");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        } else {
            double val = Integer.parseInt(value.toString());
            if (val < 1 || val > 10000) {
                FacesMessage msg = new FacesMessage("Количество должно быть больше 0 и меньше 10000");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

}
