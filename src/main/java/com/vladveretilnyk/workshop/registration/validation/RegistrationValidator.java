package com.vladveretilnyk.workshop.registration.validation;

import com.vladveretilnyk.workshop.password.PasswordService;
import com.vladveretilnyk.workshop.password.annotation.PasswordMatches;
import com.vladveretilnyk.workshop.registration.request.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class RegistrationValidator implements ConstraintValidator<PasswordMatches, RegistrationRequest> {

    private PasswordService passwordService;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegistrationRequest registrationRequest, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = isValid(registrationRequest);

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("matchingPassword").addConstraintViolation();
        }

        return isValid;
    }


    private boolean isValid(RegistrationRequest registrationRequest) {
        return passwordService.isMatches(registrationRequest.getPassword(), registrationRequest.getMatchingPassword());
    }
}