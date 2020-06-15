package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Aprovacao;
import com.br.condomio.apt.domain.InquilinoSituacao;
import com.br.condomio.apt.domain.Notificacao;
import com.br.condomio.apt.domain.enums.ObjetivoInquilino;
import com.br.condomio.apt.domain.enums.StatusPessoa;
import com.br.condomio.apt.dto.*;
import com.br.condomio.apt.repository.*;
import com.br.condomio.apt.service.exception.BusinessServiceException;
import com.br.condomio.apt.service.exception.UnprocessableEntityException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private BlocoService blocoService;

    @Autowired
    private SindicoRepository sindicoRepository;

    @Autowired
    private ApartamentoRepository repository;

    @Autowired
    private AprovacaoRepository aprovacaoRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private InquilinoRepository inquilinoRepository;

    @Autowired
    private ConvidadoRepository convidadoRepository;


    @Autowired
    private ModelMapper mapper;

    public Apartamento findById(String id){
        return repository.findById(id).get();
    }

    public List<ApartamentoDTO> getAllByBloco(String id) {
        return blocoRepository.findById(id).get().getApartamentos().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void saveInquilino(String id, InquilinoDTO inquilinoDTO) {
        repository.findById(id).ifPresentOrElse(

                apartamento -> {
                    if(apartamento.getInquilino()!=null){
                        throw new UnprocessableEntityException("apartamento já possui inquilino");
                    }

                    var condominio = propriedadeRepository.findPropriedadeByCnpj(apartamento.getCondomioCnpj());
                    var inquilino = inquilinoRepository.findById(inquilinoDTO.getId()).get();
                    var aprov = aprovacaoRepository.findAprovacaoByApartamentoId(apartamento.getId());
                    if(aprov.isPresent()){
                       throw new BusinessServiceException("Já existe solicitação para apartamento");
                    }

                    var bloco = blocoRepository.findBlocoByBuscadorBloco(apartamento.getBuscadorBloco());
                    InquilinoSituacao situacao = InquilinoSituacao.builder().
                            apartamentoId(id)
                            .apartamentoNome(apartamento.getNome())
                            .objetivoInquilino(ObjetivoInquilino.MORADOR)
                            .statusPessoa(StatusPessoa.ANALISE)
                            .blocoId(bloco.get().getId())
                            .condominio(condominio.get().getNome())
                            .condominioId(condominio.get().getId())
                            .build();

                    Aprovacao aprovacao = Aprovacao.builder().
                            inquilinoId(inquilino.getId())
                            .apartamentoId(apartamento.getId())
                            .propriedadeId(condominio.get().getId())
                            .inquilinoNome(inquilino.getNome())
                            .telefone(inquilino.getTelefone())
                            .objetivoInquilino(ObjetivoInquilino.MORADOR)
                            .statusPessoa(StatusPessoa.ANALISE).
                            build();
                    var apr = aprovacaoRepository.save(aprovacao);

                    var sindico = sindicoRepository.findById(condominio.get().getSindico().getId()).get();

                    sindico.getAprovacaos().add(apr);

                    sindicoRepository.save(sindico);


                    inquilino.getInquilinoSituacaos().add(situacao);

                    inquilinoRepository.save(inquilino);

                },

                ()->{throw new BusinessServiceException("Não foi possívelr realizar requisição");});

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

    public void addConvidado(String id, ConvidadoDTO convidadoDTO) {
        repository.findById(id).ifPresentOrElse(

                apartamento -> {
                    var condominio = propriedadeRepository.findPropriedadeByCnpj(apartamento.getCondomioCnpj());
                    if(apartamento.getInquilino().getId()==null){
                        throw new BusinessServiceException("Apartamento não possui morado");
                    }
                    var inquilino = inquilinoRepository.findById(apartamento.getInquilino().getId()).get();
                    var bloco = blocoRepository.findBlocoByBuscadorBloco(apartamento.getBuscadorBloco());
                    var convidado = convidadoRepository.findById(convidadoDTO.getId()).get();
                    InquilinoSituacao situacao = InquilinoSituacao.builder().
                            apartamentoId(id)
                            .apartamentoNome(apartamento.getNome())
                            .objetivoInquilino(ObjetivoInquilino.VISITANTE)
                            .statusPessoa(StatusPessoa.ANALISE)
                            .blocoId(bloco.get().getId())
                            .condominio(condominio.get().getNome())
                            .condominioId(condominio.get().getId())
                            .build();

                    Aprovacao aprovacao = Aprovacao.builder().
                            inquilinoId(inquilino.getId())
                            .apartamentoId(apartamento.getId())
                            .propriedadeId(condominio.get().getId())
                            .inquilinoNome(inquilino.getNome())
                            .telefone(inquilino.getTelefone())
                            .convidadoId(convidado.getId())
                            .objetivoInquilino(ObjetivoInquilino.VISITANTE)
                            .statusPessoa(StatusPessoa.ANALISE).
                                    build();
                    var apr = aprovacaoRepository.save(aprovacao);

                    inquilino.getAprovacaos().add(apr);

                    inquilinoRepository.save(inquilino);

                    convidado.getConvidadoSituacaos().add(situacao);

                    convidadoRepository.save(convidado);

                },

                ()->{throw new RuntimeException();});

    }
}
