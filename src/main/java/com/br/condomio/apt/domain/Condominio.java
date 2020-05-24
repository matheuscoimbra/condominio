package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.Arquitetura;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @NotNull(message = "Informe a arquitetura")
    private Arquitetura arquitetura;

    private List<Bloco> blocos;

    @NotNull(message = "Informe a quantidade de BLOCO/PREDIO")
    private Integer quantidadeArquitetura;

    @NotNull(message = "Informe a quantidade de andares por BLOCO/PREDIO")
    private Integer quantidadeAndar;

    @NotNull(message = "Informe a quantidade de apartamentos por BLOCO/PREDIO")
    private Integer quantidadeApartamento;

    private String start;

    private String intervalo;



}
