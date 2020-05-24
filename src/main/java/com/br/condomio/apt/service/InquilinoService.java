package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.repository.InquilinoRepository;
import com.br.condomio.apt.service.exception.InvalidPhoneNumberException;
import com.br.condomio.apt.service.exception.ObjectNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InquilinoService {

    @Autowired
    private InquilinoRepository repository;

    public Inquilino save(Inquilino inquilino){
        String newCell = nationalNumber(inquilino.getTelefone());
        inquilino.setTelefone(newCell);
        return repository.save(inquilino);
    }

    public Inquilino find(String telefone){
        String cell = nationalNumber(telefone);
        var inquilino = repository.findInquilinoByTelefone(cell);
        if(inquilino.isPresent()){
            return inquilino.get();
        }else{
            throw new ObjectNotFoundException("Usuário não existe");
        }
    }

    public String nationalNumber(String cell){
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(cell, "BR");
            return String.valueOf(number.getNationalNumber());
        }catch (NumberParseException e){
            throw new InvalidPhoneNumberException("Número de telefone incorreto");
        }
    }
}
