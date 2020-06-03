package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Convidado;
import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.dto.ConvidadoDTO;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.repository.ConvidadoRepository;
import com.br.condomio.apt.repository.InquilinoRepository;
import com.br.condomio.apt.service.exception.InvalidPhoneNumberException;
import com.br.condomio.apt.service.exception.ObjectNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvidadoService {

    @Autowired
    private ConvidadoRepository repository;

    @Autowired
    private ModelMapper mapper;

    public Convidado save(ConvidadoDTO convidadoDTO){

        Convidado inquilino1 = mapper.map(convidadoDTO,Convidado.class);
        String newCell = nationalNumber(convidadoDTO.getTelefone());
        inquilino1.setTelefone(newCell);
        return repository.save(inquilino1);
    }

    public Convidado find(String telefone){
        String cell = nationalNumber(telefone);
        var convidado = repository.findConvidadoByTelefone(cell);
        if(convidado.isPresent()){
            return convidado.get();
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

    public Convidado findById(String id) {
        return repository.findById(id).get();
    }
}
