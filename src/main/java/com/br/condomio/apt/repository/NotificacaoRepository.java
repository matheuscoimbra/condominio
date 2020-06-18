package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Notificacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface NotificacaoRepository extends MongoRepository<Notificacao, String> {


    List<Notificacao> findAllByInquilino(String inquilino);
}
