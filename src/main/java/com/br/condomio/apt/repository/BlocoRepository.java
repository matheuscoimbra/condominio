package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Bloco;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BlocoRepository extends MongoRepository<Bloco, String> {

    Optional<Bloco> findBlocoByBuscadorBloco(String buscadorBloco);
}
