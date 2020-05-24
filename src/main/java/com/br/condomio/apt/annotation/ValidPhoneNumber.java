package com.br.condomio.apt.annotation;


import com.br.condomio.apt.config.PhoneNumberValidatorConfig;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidatorConfig.class)
public @interface ValidPhoneNumber {
    String message() default "doesn't seem to be a valid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}