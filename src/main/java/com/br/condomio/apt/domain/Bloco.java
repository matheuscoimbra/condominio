package com.br.condomio.apt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "bloco")
public class Bloco {

    @Id
    private String id;

    private String buscadorBloco;

    private String nome;
    @JsonIgnore
    @DBRef
    private List<Apartamento> apartamentos;
}
