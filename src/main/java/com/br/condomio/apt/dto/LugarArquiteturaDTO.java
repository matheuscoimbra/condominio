package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.enums.Arquitetura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LugarArquiteturaDTO implements Serializable {

    private String id;
    private String nome;
    private Integer andar;
    private Boolean temMorador;
}
