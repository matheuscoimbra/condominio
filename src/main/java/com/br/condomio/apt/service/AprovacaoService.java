package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.*;
import com.br.condomio.apt.domain.enums.StatusInquilino;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.repository.ApartamentoRepository;
import com.br.condomio.apt.repository.AprovacaoRepository;
import com.br.condomio.apt.repository.InquilinoRepository;
import com.br.condomio.apt.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AprovacaoService {

    @Autowired
    private SindicoService sindicoService;

    @Autowired
    private InquilinoRepository inquilinoRepository;

    @Autowired
    private AprovacaoRepository repository;

    @Autowired
    private ApartamentoRepository apartamentoRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;


    public void mudaStatus(String aprovacaoId, StatusInquilino status) {

        Aprovacao aprovacao = repository.findById(aprovacaoId).get();
        Inquilino inquilino = inquilinoRepository.findById(aprovacao.getInquilinoId()).get();

        if(status.equals(StatusInquilino.APROVADO)){

            Propriedade propriedade = propriedadeRepository.findById(aprovacao.getPropriedadeId()).get();

            Apartamento apartamento = apartamentoRepository.findById(aprovacao.getApartamentoId()).get();
            InquilinoDTO inquilinoDTO = new InquilinoDTO();
            inquilinoDTO.setStatusInquilino(StatusInquilino.APROVADO);
            inquilinoDTO.setNome(inquilino.getNome());
            inquilinoDTO.setTelefone(inquilino.getTelefone());
            apartamento.setInquilino(inquilinoDTO);
            apartamento.setNotificacaos(new ArrayList<>());
            apartamentoRepository.save(apartamento);

            ApartamentoProp propriedadeInq = ApartamentoProp.builder()
                    .apartamentoId(apartamento.getId())
                    .apartamentoNome(apartamento.getNome())
                    .propriedadeNome(propriedade.getNome())
                    .propriedadeCnpj(propriedade.getCnpj()).build();

            InquilinoSituacao inquilinoSituacao = inquilino.getInquilinoSituacaos().stream()
                    .filter(inquilinoSituacao1 -> inquilinoSituacao1.getApartamentoId().equals(aprovacao.getApartamentoId()))
                    .findAny().get();
            inquilino.getInquilinoSituacaos().remove(inquilinoSituacao);
            inquilino.getPropriedades().add(propriedadeInq);
            inquilinoRepository.save(inquilino);

            repository.delete(aprovacao);

        }
        if(status.equals(StatusInquilino.RECUSADO)){
            InquilinoSituacao inquilinoSituacao = inquilino.getInquilinoSituacaos().stream()
                    .filter(inquilinoSituacao1 -> inquilinoSituacao1.getApartamentoId().equals(aprovacao.getApartamentoId()))
                    .findAny().get();
            inquilino.getInquilinoSituacaos().remove(inquilinoSituacao);
            inquilinoRepository.save(inquilino);

            repository.delete(aprovacao);
            //TODO algo
        }


    }
}
