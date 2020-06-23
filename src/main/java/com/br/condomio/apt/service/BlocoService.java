package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Bloco;
import com.br.condomio.apt.domain.Propriedade;
import com.br.condomio.apt.domain.enums.Arquitetura;
import com.br.condomio.apt.dto.*;
import com.br.condomio.apt.repository.BlocoRepository;
import com.br.condomio.apt.repository.PropriedadeRepository;
import com.br.condomio.apt.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlocoService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private BlocoRepository repository;

    @Autowired
    private ModelMapper mapper;

    public Bloco getByApt(String apartamentoId){
        Bloco bloco = Bloco.builder().apartamentos(List.of(Apartamento.builder().id(apartamentoId).build())).build();

        Example<Bloco> example = Example.of(bloco,
                ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreCase());

        var result = repository.findOne(example);

        return result.get();
    }

    public ApartamentoRespDTO toDTO(Apartamento apartamento){
        var apt =  mapper.map(apartamento, ApartamentoRespDTO.class);
        var inq = mapper.map(apartamento.getInquilino(), InquilinoDTO.class);
        apt.setInquilino(inq);
        return apt;
    }


    public ArquiteturaDTO getAll(String condominioId) {
        ArquiteturaDTO arquiteturaDTO = new ArquiteturaDTO();
        propriedadeRepository.findById(condominioId).ifPresentOrElse(
                (prop) -> {

                    if(prop.getArquitetura().equals(Arquitetura.BLOCO)){
                       List<Bloco> blocos =  prop.getBlocos();
                       arquiteturaDTO.setBlocos(blocos);
                       arquiteturaDTO.setProximaEtapa(true);
                    }else if(prop.getArquitetura().equals(Arquitetura.PREDIO)){
                        List<ApartamentoRespDTO> salas =  prop.getBlocos().get(1).getApartamentos().stream().map(this::toDTO).collect(Collectors.toList());
                        arquiteturaDTO.setSalas(salas);
                        arquiteturaDTO.setProximaEtapa(false);
                    }else if(prop.getArquitetura().equals(Arquitetura.CASA)){
                        List<ApartamentoRespDTO> casas =  prop.getBlocos().get(1).getApartamentos().stream().map(this::toDTO).collect(Collectors.toList());
                        arquiteturaDTO.setSalas(casas);
                        arquiteturaDTO.setProximaEtapa(false);
                    }
                },
                () ->{ throw new ObjectNotFoundException("Propriedade inexistente");}
        );
        return arquiteturaDTO;
    }

    public BlocoDTO toDTO(Bloco bloco){
        return mapper.map(bloco, BlocoDTO.class);
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
