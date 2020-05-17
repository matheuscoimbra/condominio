package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Bloco;
import com.br.condomio.apt.domain.Condominio;
import com.br.condomio.apt.repository.ApartamentoRepository;
import com.br.condomio.apt.repository.BlocoRepository;
import com.br.condomio.apt.repository.CondominioRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CondominioService {

    @Autowired
    private CondominioRepository repository;

    @Autowired
    private ApartamentoRepository apartamentoRepository;

    @Autowired
    private BlocoRepository blocoRepository;


    public Condominio save(Condominio condominio){

        Integer quantidadeBlocos = condominio.getQuantidadeArquitetura();
        Integer quantidadeApartamentos = condominio.getQuantidadeApartamento();
        Integer quantidadeAndares = condominio.getQuantidadeAndar();
        String prefixoBloco = condominio.getArquitetura().getDescricao();
        String StartAp = condominio.getStart();
        List<Bloco> blocoList = new ArrayList<>();
        int leftpad = 0;
        if( condominio.getStart().equals("00")){
            leftpad = 1;
        }
        if( condominio.getStart().equals("000")){
            leftpad = 2;
        }

        for (int i = 0; i < quantidadeBlocos; i++) {
            List<Apartamento> apartamentoList = new ArrayList<>();

            for (int j = 0; j < quantidadeAndares; j++) {

                for (int k = 0; k < quantidadeApartamentos ; k++) {

                    Apartamento apartamento = Apartamento.builder()
                            .andar(j+1).nome(String.valueOf(j+1)+StringUtils.leftPad(String.valueOf(k), leftpad, "0")).build();
                    apartamentoList.add(apartamento);
                }

            }
            apartamentoList = apartamentoRepository.saveAll(apartamentoList);

            Bloco bloco = Bloco.builder().apartamentos(apartamentoList).nome(prefixoBloco+(i+1)).build();
            blocoList.add(bloco);
        }
        blocoList = blocoRepository.saveAll(blocoList);
        condominio.setBlocos(blocoList);
        repository.save(condominio);

        return condominio;
    }


    public void changeName(String id, String name) {
        repository.findById(id).ifPresentOrElse(

                apartamento -> {

                    apartamento.setNome(name);
                    repository.save(apartamento);
                },

                ()->{throw new RuntimeException();});
    }
}
