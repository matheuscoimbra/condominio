package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.enums.ObjetivoInquilino;
import com.br.condomio.apt.domain.enums.PerfilInquilino;
import com.br.condomio.apt.domain.enums.StatusPessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class InquilinoDTO implements Serializable {

    private String id;
    @NotBlank(message = "Informe o telefone")
    private String telefone;
    @NotBlank(message = "Informe o nome")
    private String nome;
    private String token;
    @JsonIgnore
    private StatusPessoa statusPessoa;

}
