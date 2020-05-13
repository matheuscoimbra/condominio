package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.enums.Motivo;
import lombok.Data;

import java.io.Serializable;

@Data
public class NotificacaoDTO implements Serializable {

    private Motivo motivo;
    private String descrição;
    private Integer peso;

    public Integer getPeso(){
        return motivo.getCod();
    }
}
