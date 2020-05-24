package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.domain.Inquilino;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InquilinoRepository extends MongoRepository<Inquilino, String> {

    Optional<Inquilino> findInquilinoByTelefone(String telefone);

}
