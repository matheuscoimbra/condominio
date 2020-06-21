package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.enums.Motivo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class NotificacaoDTO implements Serializable {

    @NotNull(message = "Informe o motivo")
    private Motivo motivo;
    @NotBlank(message = "Informe a descricao")
    private String descricao;
    private Integer peso;

    public Integer getPeso(){
        return motivo.getCod();
    }
}
