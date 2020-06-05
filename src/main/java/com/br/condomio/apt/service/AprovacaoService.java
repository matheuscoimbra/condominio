package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.*;
import com.br.condomio.apt.domain.enums.StatusPessoa;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.repository.*;
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
    private ConvidadoRepository convidadoRepository;

    @Autowired
    private AprovacaoRepository repository;

    @Autowired
    private ApartamentoRepository apartamentoRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;


    public void mudaStatus(String aprovacaoId, StatusPessoa status) {

        Aprovacao aprovacao = repository.findById(aprovacaoId).get();
        Inquilino inquilino = inquilinoRepository.findById(aprovacao.getInquilinoId()).get();

        if(status.equals(StatusPessoa.APROVADO)){

            Propriedade propriedade = propriedadeRepository.findById(aprovacao.getPropriedadeId()).get();

            Apartamento apartamento = apartamentoRepository.findById(aprovacao.getApartamentoId()).get();
            InquilinoDTO inquilinoDTO = new InquilinoDTO();
            inquilinoDTO.setStatusPessoa(StatusPessoa.APROVADO);
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
        if(status.equals(StatusPessoa.RECUSADO)){
            InquilinoSituacao inquilinoSituacao = inquilino.getInquilinoSituacaos().stream()
                    .filter(inquilinoSituacao1 -> inquilinoSituacao1.getApartamentoId().equals(aprovacao.getApartamentoId()))
                    .findAny().get();
            inquilino.getInquilinoSituacaos().remove(inquilinoSituacao);
            inquilinoRepository.save(inquilino);

            repository.delete(aprovacao);
            //TODO algo
        }


    }

    public void mudaStatusConvidado(String aprovacaoId, StatusPessoa status) {

        Aprovacao aprovacao = repository.findById(aprovacaoId).get();
        Inquilino inquilino = inquilinoRepository.findById(aprovacao.getInquilinoId()).get();
        Convidado convidado = convidadoRepository.findById(aprovacao.getConvidadoId()).get();
        if(status.equals(StatusPessoa.APROVADO)){

            Propriedade propriedade = propriedadeRepository.findById(aprovacao.getPropriedadeId()).get();

            Apartamento apartamento = apartamentoRepository.findById(aprovacao.getApartamentoId()).get();

            ApartamentoProp propriedadeInq = ApartamentoProp.builder()
                    .apartamentoId(apartamento.getId())
                    .apartamentoNome(apartamento.getNome())
                    .propriedadeNome(propriedade.getNome())
                    .propriedadeCnpj(propriedade.getCnpj())
                    .inquilinoId(inquilino.getId()).build();

            InquilinoSituacao inquilinoSituacao = convidado.getConvidadoSituacaos().stream()
                    .filter(inquilinoSituacao1 -> inquilinoSituacao1.getApartamentoId().equals(aprovacao.getApartamentoId()))
                    .findAny().get();
            convidado.getConvidadoSituacaos().remove(inquilinoSituacao);
            convidado.getPropriedades().add(propriedadeInq);
            inquilino.getConvidados().add(convidado);
            inquilinoRepository.save(inquilino);
            convidadoRepository.save(convidado);
            repository.delete(aprovacao);

        }
        if(status.equals(StatusPessoa.RECUSADO)){
            InquilinoSituacao inquilinoSituacao = convidado.getConvidadoSituacaos().stream()
                    .filter(inquilinoSituacao1 -> inquilinoSituacao1.getApartamentoId().equals(aprovacao.getApartamentoId()))
                    .findAny().get();
            convidado.getConvidadoSituacaos().remove(inquilinoSituacao);
            convidadoRepository.save(convidado);

            repository.delete(aprovacao);
            //TODO algo
        }
    }
}
