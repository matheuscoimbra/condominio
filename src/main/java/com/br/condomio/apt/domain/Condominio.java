package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.Arquitetura;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "condominio")
public class Condominio implements Serializable {

    @Id
    private String id;

    private String cnpj;

    private String nome;

    private Sindico sindico;

    private String propietario;

    private Porteiro porteiro;

    private Arquitetura arquitetura;

    private List<Bloco> blocos;

    private Integer quantidadeArquitetura;

    private Integer quantidadeAndar;

    private Integer quantidadeApartamento;

    private String start;

    private String intervalo;



}
