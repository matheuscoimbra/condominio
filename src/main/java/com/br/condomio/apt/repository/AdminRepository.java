package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.domain.Apartamento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {

    Optional<Admin> findAdminByCnpjAndAndSenha(String cnpj, String senha);
    Optional<Admin> findAdminByCnpj(String cnpj);
}
