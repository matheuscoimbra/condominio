package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Bloco;
import com.br.condomio.apt.dto.BlocoDTO;
import com.br.condomio.apt.repository.BlocoRepository;
import com.br.condomio.apt.repository.PropriedadeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<BlocoDTO> getAll(String condominioId) {
        return propriedadeRepository.findById(condominioId).get().getBlocos().stream().map(this::toDTO).collect(Collectors.toList());
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
