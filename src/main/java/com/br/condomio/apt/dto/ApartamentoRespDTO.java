package com.br.condomio.apt.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApartamentoRespDTO implements Serializable {

    private String id;

    private String nome;

    private Integer andar;

    private InquilinoDTO inquilino;
}
