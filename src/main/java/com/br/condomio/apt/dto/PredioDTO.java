package com.br.condomio.apt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PredioDTO implements Serializable {


    private String id;

    @NotEmpty(message ="Informe o CNPJ")
    private String cnpj;

    @NotEmpty(message = "Informe o nome do condomínio")
    private String nome;

    @NotNull(message = "Informe a quantidade de andares por BLOCO/PREDIO")
    private Integer quantidadeAndar;

    @NotNull(message = "Informe a quantidade de apartamentos por BLOCO/PREDIO")
    private Integer quantidadeSalas;

    private String start;

    private String intervalo;

    private String bairro;

    private String cidade;

    private String uf;

    @NotNull(message = "Informe o cep")
    private String cep;

    private String numero;

    private String complemento;

    private String latitude;

    private String longitude;


}
