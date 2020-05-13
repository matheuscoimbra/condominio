package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Bloco;
import com.br.condomio.apt.domain.Condominio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlocoRepository extends MongoRepository<Bloco, String> {


}
