package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Porteiro;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PorteiroRepository extends MongoRepository<Porteiro, String> {
}
