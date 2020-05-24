package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.domain.Notificacao;
import com.br.condomio.apt.domain.enums.StatusInquilino;
import com.br.condomio.apt.dto.ApartamentoDTO;
import com.br.condomio.apt.dto.ChangeBetweenDTO;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.dto.NotificacaoDTO;
import com.br.condomio.apt.repository.ApartamentoRepository;
import com.br.condomio.apt.repository.BlocoRepository;
import com.br.condomio.apt.repository.CondominioRepository;
import com.br.condomio.apt.repository.InquilinoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApartamentoService {
    
    @Autowired
    private BlocoRepository blocoRepository;

    @Autowired
    private ApartamentoRepository repository;

    @Autowired
    private CondominioRepository condominioRepository;

    @Autowired
    private InquilinoRepository inquilinoRepository;

    @Autowired
    private ModelMapper mapper;

    public List<ApartamentoDTO> getAllByBloco(String id) {
        return blocoRepository.findById(id).get().getApartamentos().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void saveInquilino(String id, InquilinoDTO inquilinoDTO) {
        repository.findById(id).ifPresentOrElse(

                apartamento -> {
                    var condominio = condominioRepository.findCondominioByCnpj(apartamento.getCondomioCnpj());
                    var inquilino = inquilinoRepository.findById(inquilinoDTO.getId()).get();
                    inquilino.setStatusInquilino(List.of(Map.of(id,StatusInquilino.ANALISE)));
                    inquilino.setApartamentosCondList(List.of(Map.of(condominio.get().getNome(),apartamento.getId())));
                    inquilinoRepository.save(inquilino);
                    inquilinoDTO.setStatusInquilino(StatusInquilino.ANALISE);
                    apartamento.setInquilino(inquilinoDTO);
                    apartamento.setNotificacaos(new ArrayList<>());
                    repository.save(apartamento);
                },

                ()->{throw new RuntimeException();});

    }


    public ApartamentoDTO toDTO(Apartamento apartamento){
        return mapper.map(apartamento, ApartamentoDTO.class);
    }

    public void changeName(String id, String name) {
        repository.findById(id).ifPresentOrElse(

                apartamento -> {

                    apartamento.setNome(name);
                    repository.save(apartamento);
                },

                ()->{throw new RuntimeException();});

    }

    public void removeInquilino(String id) {
        repository.findById(id).ifPresentOrElse(

                apartamento -> {
                    apartamento.setInquilino(null);
                    apartamento.setNotificacaos(new ArrayList<>());
                    repository.save(apartamento);
                },

                ()->{throw new RuntimeException();});

    }

    public void delete(String id) {
        repository.findById(id).ifPresentOrElse(

                apartamento -> {
                    repository.delete(apartamento);
                },

                ()->{throw new RuntimeException();});

    }

    public void notifyInquilino(String id, NotificacaoDTO notificacaoDTO) {

        var apt =  repository.findById(id).get();
        if(apt.getInquilino()!=null){
            var notificacao = mapper.map(notificacaoDTO, Notificacao.class);
            apt.getNotificacaos().add(notificacao);
            repository.save(apt);
        }else{
            throw new RuntimeException("Apartamento sem inquilino");
        }
    }

    public List<ApartamentoDTO> changeBetWeen(ChangeBetweenDTO changeBetweenDTO) {
        var aptFrom =  repository.findById(changeBetweenDTO.getGetApartementoIdFrom()).get();
        var aptTo =  repository.findById(changeBetweenDTO.getGetApartementoIdFrom()).get();


        var inquilinoTo = aptTo==null?null:aptTo.getInquilino();
        var inquilinoFrom = aptFrom==null?null:aptFrom.getInquilino();

        aptTo.setInquilino(inquilinoFrom);
        aptFrom.setInquilino(inquilinoTo);

        repository.saveAll(List.of(aptTo,aptFrom));

        return blocoRepository.findById(changeBetweenDTO.getBlocoId()).get().getApartamentos().stream().map(this::toDTO).collect(Collectors.toList());

    }
}
