package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.ApartamentoProp;
import com.br.condomio.apt.domain.Convidado;
import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.domain.InquilinoSituacao;
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

import java.util.List;

@Service
public class InquilinoService {

    @Autowired
    private InquilinoRepository repository;

    @Autowired
    private ConvidadoRepository convidadoRepository;

    @Autowired
    private ModelMapper mapper;

    public Inquilino save(InquilinoDTO inquilino){

        Inquilino inquilino1 = mapper.map(inquilino,Inquilino.class);
        String newCell = nationalNumber(inquilino.getTelefone());
        inquilino1.setTelefone(newCell);
        return repository.save(inquilino1);
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

    public Inquilino findById(String id) {
        return repository.findById(id).get();
    }

    public List<Convidado> getAllConvidados(String id) {
        Inquilino inquilino = findById(id);
        return inquilino.getConvidados();
    }

    public void deleteConvidado(String inquiniloId, String id) {
        var convidado = convidadoRepository.findById(id).get();
        Inquilino inquilino = findById(inquiniloId);
        ApartamentoProp prop = convidado.getPropriedades().stream()
                .filter(apartamentoProp -> apartamentoProp.getInquilinoId().equals(inquilino.getId()))
                .findAny().get();
        convidado.getPropriedades().remove(prop);
        inquilino.getConvidados().remove(convidado);

        repository.save(inquilino);
        convidadoRepository.save(convidado);


    }
}
