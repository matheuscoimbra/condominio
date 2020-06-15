package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Aprovacao;
import com.br.condomio.apt.domain.Bloco;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AprovacaoRepository extends MongoRepository<Aprovacao, String> {

    Optional<Aprovacao> findAprovacaoByApartamentoId(String apartamentoId);
}
