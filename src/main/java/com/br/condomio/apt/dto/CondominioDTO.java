package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Bloco;
import com.br.condomio.apt.domain.Porteiro;
import com.br.condomio.apt.domain.Sindico;
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
public class CondominioDTO implements Serializable {

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



}
