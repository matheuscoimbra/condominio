package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.domain.Notificacao;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BlocoDTO implements Serializable {

    @NotEmpty(message ="Informe o CNPJ")
    private String cnpj;

    @NotEmpty(message = "Informe o nome do condomínio")
    private String nome;

    @NotNull(message = "Informe a quantidade de BLOCO")
    private Integer quantidadeBlocos;

    @NotNull(message = "Informe a quantidade de andares por BLOCO/PREDIO")
    private Integer quantidadeAndar;

    @NotNull(message = "Informe a quantidade de apartamentos por BLOCO/PREDIO")
    private Integer quantidadeApartamento;

    private String start;

    private String intervalo;

    private String bairro;

    private String cidade;

    private String uf;

    private String cep;

    private String numero;

    private String complemento;

    private String latitude;

    private String longitude;
}
