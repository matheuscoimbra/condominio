package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Sindico;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SindicoRepository extends MongoRepository<Sindico, String> {

    Optional<Sindico> findSindicoByTelefone(String telefone);
    Optional<Sindico> findSindicoByCpf(String cpf);
}
