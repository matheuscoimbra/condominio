package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Porteiro;
import com.br.condomio.apt.domain.Sindico;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PorteiroRepository extends MongoRepository<Porteiro, String> {

    Optional<Sindico> findPorteiroByTelefone(String telefone);
    Optional<Sindico> findPorteiroByCpf(String cpf);
}
