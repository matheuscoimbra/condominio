package com.br.condomio.apt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "sindico")
public class Sindico {
    @Id
    private String id;
    private String foto;
    private String cpf;
    private String telefone;
    private String nome;
    private List<String> prediosId;
    private Boolean sindico = true;

}

