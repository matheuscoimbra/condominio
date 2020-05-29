package com.br.condomio.apt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "sindico")
public class Sindico extends Usuario{
    @Id
    private String id;
    private String foto;
    private String telefone;
    private String nome;
    private List<PropriedadeProp> propriedadeSindico =  new ArrayList<>();
    @DBRef
    private List<Aprovacao> aprovacaos =  new ArrayList<>();
    private boolean sindico = true;

}

