package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Bloco;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ApartamentoRepository extends MongoRepository<Apartamento, String> {

        List<Apartamento> findAllByAndar(Integer andar);

        List<Apartamento> findAllByAndarAndBuscadorBloco(Integer andar, String buscadorBloco);

        List<Apartamento> findAllByAndarAndCondomioCnpj(Integer andar, String condomioCnpj);

}
