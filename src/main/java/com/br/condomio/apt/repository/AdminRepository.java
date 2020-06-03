package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.domain.Apartamento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {

    Optional<Admin> findAdminByCpfAndAndSenha(String cnpj, String senha);
    Optional<Admin> findAdminByCpf(String cnpj);

    Optional<Admin> findAdminByEmail(String email);
}
