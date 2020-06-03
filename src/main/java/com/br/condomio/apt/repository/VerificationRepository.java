package com.br.condomio.apt.repository;

import com.br.condomio.apt.domain.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VerificationRepository extends MongoRepository<VerificationToken, String> {

    Optional<VerificationToken> findFirstByTokenEquals(String token);

}
