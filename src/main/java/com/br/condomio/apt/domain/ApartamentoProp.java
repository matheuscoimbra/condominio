package com.br.condomio.apt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApartamentoProp {

    private String apartamentoId;
    private String apartamentoNome;
    private String propriedadeNome;
    private String propriedadeCnpj;
    private String inquilinoId;
}
