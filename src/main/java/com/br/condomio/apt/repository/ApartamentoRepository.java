package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Bloco;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApartamentoRepository extends MongoRepository<Apartamento, String> {


}
