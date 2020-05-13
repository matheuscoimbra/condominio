package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.Motivo;
import lombok.Data;

@Data
public class Notificacao {

    private Motivo motivo;
    private String descrição;
    private Integer peso;

    public Integer getPeso(){
        return motivo.getCod();
    }


}
