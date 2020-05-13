package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.StatusInquilino;
import lombok.Data;

@Data
public class Inquilino {

    private String id;
    private String telefone;
    private String nome;
    private StatusInquilino statusInquilino;


}
