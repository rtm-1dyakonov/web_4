package com.validators;

import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("priceValidator")
public class PriceValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Pattern pattern = Pattern.compile("[0-9]+\\.[0-9]*");
        if (!pattern.matcher(value.toString()).find()) {
            FacesMessage msg = new FacesMessage("Цена должна быть числом с плавающей точкой");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        } else {
            double val = Double.parseDouble(value.toString());
            if (val < 0.1 || val > 999999999.00) {
                FacesMessage msg = new FacesMessage("Цена должна быть больше 0.1 и меньше 999999999.00");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

}
