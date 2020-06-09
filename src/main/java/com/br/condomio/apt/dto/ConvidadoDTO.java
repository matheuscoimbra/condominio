package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.enums.ObjetivoInquilino;
import com.br.condomio.apt.domain.enums.PerfilInquilino;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ConvidadoDTO implements Serializable {

    private String id;
    @NotBlank(message = "Informe o telefone")
    private String telefone;
    @NotBlank(message = "Informe o nome")
    private String nome;

}
