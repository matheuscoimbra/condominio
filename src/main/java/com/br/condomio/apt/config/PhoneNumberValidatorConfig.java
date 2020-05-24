package com.br.condomio.apt.config;


import com.br.condomio.apt.annotation.ValidPhoneNumber;
import com.br.condomio.apt.service.exception.InvalidPhoneNumberException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.beans.factory.annotation.Configurable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Configurable
public class PhoneNumberValidatorConfig implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {


        value = value.replaceAll("\\s", "");

        if ("".equals(value)) {
            return false;
        }

        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber number = phoneUtil.parseAndKeepRawInput(value, "BR");

            boolean isValid  = phoneUtil.isValidNumber(number);
            boolean  isPossible = phoneUtil.isPossibleNumber(number);
            return  isValid;

        } catch (Exception e) {
            throw new InvalidPhoneNumberException("Informe um número de telefone válido");
        }

    }
}
