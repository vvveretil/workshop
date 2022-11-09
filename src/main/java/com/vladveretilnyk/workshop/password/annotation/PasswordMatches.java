package com.vladveretilnyk.workshop.password.annotation;

import com.vladveretilnyk.workshop.registration.validation.RegistrationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = {TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = RegistrationValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Passwords do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}