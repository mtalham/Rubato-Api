package no.rubato.validator;

import no.rubato.model.Persons;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Persons.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Persons persons = (Persons) object;
        if(persons.getPassword().length() < 5){
            errors.rejectValue("password","length", "Password must be at least 6 characters long");
        }
        if(!persons.getPassword().equals(persons.getConfirmPassword())){
            errors.rejectValue("confirmPassword", "Match", "Password must match");
        }
    }
}
