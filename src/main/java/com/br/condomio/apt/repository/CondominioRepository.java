package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Condominio;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CondominioRepository extends MongoRepository<Condominio, String> {

    List<Condominio> findAllByPropietario(String propietario);

    Optional<Condominio> findCondominioByCnpj(String cnpj);
}
