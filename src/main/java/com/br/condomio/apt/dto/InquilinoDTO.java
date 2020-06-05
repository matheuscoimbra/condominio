package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.enums.ObjetivoInquilino;
import com.br.condomio.apt.domain.enums.PerfilInquilino;
import com.br.condomio.apt.domain.enums.StatusPessoa;
import lombok.Data;

import java.io.Serializable;

@Data
public class InquilinoDTO implements Serializable {

    private String id;
    private String telefone;
    private String nome;
    private String token;
    private StatusPessoa statusPessoa;
    private boolean inquilinoPropietario;

}
