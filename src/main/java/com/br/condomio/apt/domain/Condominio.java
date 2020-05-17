package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.Arquitetura;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "condominio")
public class Condominio implements Serializable {

    @Id
    private String id;

    @NotEmpty(message ="Informe o CNPJ")
    private String cnpj;

    @NotEmpty(message = "Informe o nome do condomínio")
    private String nome;

    private Sindico sindico;

    @NotEmpty(message = "Informe o nome do propietário")
    private String propietario;

    private Porteiro porteiro;

    @NotEmpty(message = "Informe a arquitetura")
    private Arquitetura arquitetura;

    private List<Bloco> blocos;

    @NotEmpty(message = "Informe a quantidade de BLOCO/PREDIO")
    private Integer quantidadeArquitetura;

    @NotEmpty(message = "Informe a quantidade de andares por BLOCO/PREDIO")
    private Integer quantidadeAndar;

    @NotEmpty(message = "Informe a quantidade de apartamentos por BLOCO/PREDIO")
    private Integer quantidadeApartamento;

    private String start;

    private String intervalo;



}
