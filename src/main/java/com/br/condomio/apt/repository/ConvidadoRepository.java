package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Convidado;
import com.br.condomio.apt.domain.Inquilino;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConvidadoRepository extends MongoRepository<Convidado, String> {

    Optional<Convidado> findConvidadoByTelefone(String telefone);

}
