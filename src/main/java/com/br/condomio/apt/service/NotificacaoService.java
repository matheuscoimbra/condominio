package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Notificacao;
import com.br.condomio.apt.dto.NotificacaoDTO;
import com.br.condomio.apt.dto.NotificacoesDTO;
import com.br.condomio.apt.repository.ApartamentoRepository;
import com.br.condomio.apt.repository.NotificacaoRepository;
import com.br.condomio.apt.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private ApartamentoRepository repository;

    @Autowired
    private ModelMapper mapper;

    public void notifyInquilino(String id, NotificacaoDTO notificacaoDTO) {

        var apt =  repository.findById(id).get();
        if(apt.getInquilino()!=null){
            var notificacao = mapper.map(notificacaoDTO, Notificacao.class);
            notificacao.setInquilino(apt.getInquilino().getId());
            notificacao.setApartamento(apt.getId());
            Date hoje = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            notificacao.setDate(hoje);
            notificacaoRepository.save(notificacao);
            //apt.getNotificacaos().add(notificacao);
            //repository.save(apt);
        }else{
            throw new RuntimeException("Apartamento sem inquilino");
        }
    }


    public NotificacoesDTO notifies(String id) {
        NotificacoesDTO dto = new NotificacoesDTO();
        var notificacoes =  notificacaoRepository.findAllByInquilino(id);
        dto.setTotal(notificacoes.size());
        dto.setInquilino(id);
        Date hoje = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date semanaNoti  = subtractWeeks(hoje, 1);
        Date mesNoti  = subtractMonth(hoje, 1);


        List<Notificacao> semana = notificacoes.stream().filter(notificacao ->
            ((notificacao.getDate().equals(hoje) || notificacao.getDate().before(hoje))
                    && (notificacao.getDate().equals(semanaNoti) || notificacao.getDate().after(semanaNoti))
            )
        ).collect(Collectors.toList());

        List<Notificacao> mes = notificacoes.stream().filter(notificacao ->
                ((notificacao.getDate().equals(hoje) || notificacao.getDate().before(hoje))
                        && (notificacao.getDate().equals(mesNoti) || notificacao.getDate().after(mesNoti))
                )
        ).collect(Collectors.toList());

        dto.setSemana(semana);
        dto.setMes(mes);

        return dto;
    }

    public Notificacao retornaPorId(String id){
      var noti = notificacaoRepository.findById(id).get();
      if(noti==null){
          throw new ObjectNotFoundException("Notificação inexistente");
      }
      if(noti.getLida()){
          return noti;
      }else{
          noti.setLida(true);
          notificacaoRepository.save(noti);
          return noti;
      }
    }


    public static Date subtractDays(Date date, int days) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.minusDays(days);

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date subtractMonth(Date date, int month) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.minusMonths(month);

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date subtractWeeks(Date date, int weeks) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.minusWeeks(weeks);

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
