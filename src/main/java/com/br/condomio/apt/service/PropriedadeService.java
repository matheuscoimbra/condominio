package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.*;
import com.br.condomio.apt.domain.enums.Arquitetura;
import com.br.condomio.apt.domain.PropriedadeProp;
import com.br.condomio.apt.dto.BlocoDTO;
import com.br.condomio.apt.dto.CasaDTO;
import com.br.condomio.apt.dto.PredioDTO;
import com.br.condomio.apt.dto.PropriedadeSearchDTO;
import com.br.condomio.apt.jwt.UserSS;
import com.br.condomio.apt.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository repository;

    @Autowired
    private PorteiroRepository porteiroRepository;

    @Autowired
    private SindicoRepository sindicoRepository;

    @Autowired
    private ApartamentoRepository apartamentoRepository;

    @Autowired
    private BlocoRepository blocoRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;


    @SneakyThrows
    public Propriedade saveCasa(CasaDTO dto){


        ObjectMapper mapper = new ObjectMapper();
        var condominio = modelMapper.map(dto, Propriedade.class);
        var str  = mapper.writeValueAsString(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var obj = mapper.readValue(str,UserSS.class);
        Admin admin = adminRepository.findAdminByCpf(obj.getUsername()).get();
        condominio.setQuantidadeApartamento(dto.getQuantidadeCasa());
        condominio.setArquitetura(Arquitetura.CASA);
        condominio.setQuantidadeAndar(1);
        Integer quantidadeApartamentos = condominio.getQuantidadeApartamento();
        Integer quantidadeAndares = 1;
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

        String hash = RandomStringUtils.random(6, true, true);

        List<Apartamento> apartamentoList = new ArrayList<>();

        for (int j = 0; j < quantidadeAndares; j++) {

            for (int k = 0; k < quantidadeApartamentos ; k++) {

                Apartamento apartamento = Apartamento.builder().condomioCnpj(condominio.getCnpj()).buscadorBloco(hash)
                        .andar(j+1).nome("Sala" +String.valueOf(j+1)+StringUtils.leftPad(String.valueOf(k), leftpad, "0")).build();
                apartamentoList.add(apartamento);
            }

        }
        apartamentoList = apartamentoRepository.saveAll(apartamentoList);

        Bloco bloco = Bloco.builder().buscadorBloco(hash).apartamentos(apartamentoList).nome(prefixoBloco+"_1").build();
        blocoList.add(bloco);

        blocoList = blocoRepository.saveAll(blocoList);
        condominio.setBlocos(blocoList);
        condominio.setPropietario(admin.getCpf());
        condominio.setBuscados(0);

        var condo = repository.save(condominio);
        admin.getCondominiosId().add(condo.getId());
        adminRepository.save(admin);

        return condo;
    }



    @SneakyThrows
    public Propriedade savePredio(PredioDTO dto){


        ObjectMapper mapper = new ObjectMapper();
        var condominio = modelMapper.map(dto, Propriedade.class);
        var str  = mapper.writeValueAsString(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var obj = mapper.readValue(str,UserSS.class);
        Admin admin = adminRepository.findAdminByCpf(obj.getUsername()).get();
        condominio.setQuantidadeApartamento(dto.getQuantidadeSalas());
        condominio.setArquitetura(Arquitetura.PREDIO);
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

        String hash = RandomStringUtils.random(6, true, true);

            List<Apartamento> apartamentoList = new ArrayList<>();

            for (int j = 0; j < quantidadeAndares; j++) {

                for (int k = 0; k < quantidadeApartamentos ; k++) {

                    Apartamento apartamento = Apartamento.builder().condomioCnpj(condominio.getCnpj()).buscadorBloco(hash)
                            .andar(j+1).nome("Sala" + (j + 1) +StringUtils.leftPad(String.valueOf(k), leftpad, "0")).build();
                    apartamentoList.add(apartamento);
                }

            }
            apartamentoList = apartamentoRepository.saveAll(apartamentoList);

            Bloco bloco = Bloco.builder().buscadorBloco(hash).apartamentos(apartamentoList).nome(prefixoBloco+"_1").build();
            blocoList.add(bloco);

        blocoList = blocoRepository.saveAll(blocoList);
        condominio.setBlocos(blocoList);
        condominio.setPropietario(admin.getCpf());
        condominio.setBuscados(0);

        var condo = repository.save(condominio);
        admin.getCondominiosId().add(condo.getId());
        adminRepository.save(admin);

        return condo;
    }





    @SneakyThrows
    public Propriedade save(BlocoDTO dto){
        ObjectMapper mapper = new ObjectMapper();
        var condominio = modelMapper.map(dto, Propriedade.class);
        var str  = mapper.writeValueAsString(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var obj = mapper.readValue(str,UserSS.class);
        Admin admin = adminRepository.findAdminByCpf(obj.getUsername()).get();
        condominio.setArquitetura(Arquitetura.BLOCO);
        condominio.setQuantidadeArquitetura(dto.getQuantidadeBlocos());
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
            String hash = RandomStringUtils.random(6, true, true);

            for (int j = 0; j < quantidadeAndares; j++) {

                for (int k = 0; k < quantidadeApartamentos ; k++) {

                    Apartamento apartamento = Apartamento.builder().buscadorBloco(hash).condomioCnpj(condominio.getCnpj())
                            .andar(j+1).nome((j + 1) +StringUtils.leftPad(String.valueOf(k), leftpad, "0")).build();
                    apartamentoList.add(apartamento);
                }

            }
            apartamentoList = apartamentoRepository.saveAll(apartamentoList);

            Bloco bloco = Bloco.builder().apartamentos(apartamentoList).buscadorBloco(hash).nome(prefixoBloco+(i+1)).build();
            blocoList.add(bloco);
        }
        blocoList = blocoRepository.saveAll(blocoList);
        condominio.setBlocos(blocoList);
        condominio.setPropietario(admin.getCpf());
        condominio.setBuscados(0);
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

    public List<Propriedade> getAllByPropietario(String cnpj) {
       return repository.findAllByPropietario(cnpj);
    }


    public void savePorteiroPropriedade(String idPorteiro, String idPropriedade){

        var propriedade = getById(idPropriedade);

        var porteiro = porteiroRepository.findById(idPorteiro).get();

        PorteiroProp porteiroProp = new PorteiroProp();
        porteiroProp.setId(porteiro.getId());
        porteiroProp.setNome(porteiro.getNome());
        porteiroProp.setTelefone(porteiro.getTelefone());


        PropriedadeProp propriedadeProp = new PropriedadeProp();
        propriedadeProp.setCnpj(propriedade.getCnpj());
        propriedadeProp.setNome(propriedade.getNome());
        propriedadeProp.setId(propriedade.getId());

        propriedade.setPorteiro(porteiroProp);
        propriedade.setComPorteiro(true);

        porteiro.getPropriedadePorteiro().add(propriedadeProp);

        repository.save(propriedade);
        porteiroRepository.save(porteiro);

    }

    public void saveSindicoPropriedade(String idSindico, String idPropriedade){

        var propriedade = getById(idPropriedade);

        var sindico = sindicoRepository.findById(idSindico).get();

        SindicoProp sindicoProp = new SindicoProp();
        sindicoProp.setId(sindico.getId());
        sindicoProp.setNome(sindico.getNome());

        PropriedadeProp propriedadeProp = new PropriedadeProp();
        propriedadeProp.setCnpj(propriedade.getCnpj());
        propriedadeProp.setNome(propriedade.getNome());
        propriedadeProp.setId(propriedade.getId());

        propriedade.setSindico(sindicoProp);
        propriedade.setComSindico(true);
        sindico.getPropriedadeSindico().add(propriedadeProp);

        repository.save(propriedade);
        sindicoRepository.save(sindico);

    }


    public Propriedade getById(String id) {


        Propriedade propriedade =  repository.findById(id).get();

        if(propriedade.getBuscados()==null || propriedade.getBuscados()==0){
            propriedade.setBuscados(1);
        }else{
            propriedade.setBuscados(propriedade.getBuscados()+1);
        }
        repository.save(propriedade);
        return propriedade;
    }

    public PropriedadeSearchDTO getAllByNome(String nome, String cidade) {
        PropriedadeSearchDTO searchDTO = new PropriedadeSearchDTO();
        Propriedade propriedade = Propriedade.builder().nome(nome).cidade(cidade).build();
        Example<Propriedade> example = Example.of(propriedade, ExampleMatcher.
                matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase());
        List<Propriedade> propriedades = repository.findAll(example);
        searchDTO.getPropriedades().addAll(propriedades);

        List<Propriedade> maisBuscados = propriedades.stream()
                .sorted(Comparator.comparingInt(Propriedade::getBuscados)
                        .reversed())
                .collect(Collectors.toList()).stream().filter(propriedade1 -> propriedade.getBuscados()>0).limit(5).collect(Collectors.toList());
        searchDTO.getMaisProcurados().addAll(maisBuscados);
        return searchDTO;

    }

    public PropriedadeSearchDTO getNomeSindicoNotNull(String nome,String cidade) {
        PropriedadeSearchDTO searchDTO = new PropriedadeSearchDTO();
        Propriedade propriedade = Propriedade.builder().nome(nome).cidade(cidade).comSindico(true).build();
        Example<Propriedade> example = Example.of(propriedade, ExampleMatcher.
                matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase());
        List<Propriedade> propriedades = repository.findAll(example);
        searchDTO.getPropriedades().addAll(propriedades);

        List<Propriedade> maisBuscados = propriedades.stream()
                .sorted(Comparator.comparingInt(Propriedade::getBuscados)
                        .reversed())
                .collect(Collectors.toList()).stream().filter(propriedade1 -> propriedade.getBuscados()>0).limit(5).collect(Collectors.toList());
        searchDTO.getMaisProcurados().addAll(maisBuscados);
        return searchDTO;

    }
}
