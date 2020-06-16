package com.br.condomio.apt.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CepDTO implements Serializable {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;

}
