package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Bloco;
import com.br.condomio.apt.domain.Condominio;
import com.br.condomio.apt.dto.CondominioDTO;
import com.br.condomio.apt.jwt.UserSS;
import com.br.condomio.apt.repository.AdminRepository;
import com.br.condomio.apt.repository.ApartamentoRepository;
import com.br.condomio.apt.repository.BlocoRepository;
import com.br.condomio.apt.repository.CondominioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @SneakyThrows
    public Condominio save(CondominioDTO dto){
        ObjectMapper mapper = new ObjectMapper();
        var condominio = modelMapper.map(dto,Condominio.class);
        var str  = mapper.writeValueAsString(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var obj = mapper.readValue(str,UserSS.class);
        Admin admin = adminRepository.findAdminByCpf(obj.getUsername()).get();
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

                    Apartamento apartamento = Apartamento.builder().condomioCnpj(condominio.getCnpj())
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
        condominio.setPropietario(admin.getCpf());
        var condo = repository.save(condominio);
        admin.getCondominiosId().add(condo.getId());
        adminRepository.save(admin);

        return condo;
    }


    public void changeName(String id, String name) {
        repository.findById(id).ifPresentOrElse(

                apartamento -> {

                    apartamento.setNome(name);
                    repository.save(apartamento);
                },

                ()->{throw new RuntimeException();});
    }

    public List<Condominio> getAllByPropietario(String cnpj) {
       return repository.findAllByPropietario(cnpj);
    }

    public List<Condominio> getAllByNome(String nome) {
        Condominio condominio = Condominio.builder().nome(nome).build();
        Example<Condominio> example = Example.of(condominio, ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase());
        return repository.findAll(example);
    }
}
