package com.vladveretilnyk.workshop.user.validation;

import com.vladveretilnyk.workshop.password.PasswordService;
import com.vladveretilnyk.workshop.password.annotation.PasswordMatches;
import com.vladveretilnyk.workshop.user.request.UserChangePasswordRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class UserChangePasswordValidator implements ConstraintValidator<PasswordMatches, UserChangePasswordRequest> {

    private PasswordService passwordService;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserChangePasswordRequest userChangePasswordRequest, ConstraintValidatorContext constraintValidatorContext) {
        boolean isNewPasswordValid = isNewPasswordValid(userChangePasswordRequest);

        if (!isNewPasswordValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("newPasswordMatching").addConstraintViolation();
        }

        return isNewPasswordValid;
    }

    private boolean isNewPasswordValid(UserChangePasswordRequest userChangePasswordRequest) {
        return passwordService.isMatches(userChangePasswordRequest.getNewPassword(), userChangePasswordRequest.getNewPasswordMatching());
    }

}
