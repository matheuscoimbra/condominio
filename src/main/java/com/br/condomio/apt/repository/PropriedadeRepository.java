package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Propriedade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PropriedadeRepository extends MongoRepository<Propriedade, String> {

    List<Propriedade> findAllByPropietario(String propietario);

    Optional<Propriedade> findPropriedadeByCnpj(String cnpj);
}
