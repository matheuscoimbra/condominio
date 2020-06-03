package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.enums.ObjetivoInquilino;
import com.br.condomio.apt.domain.enums.PerfilInquilino;
import com.br.condomio.apt.domain.enums.StatusInquilino;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConvidadoDTO implements Serializable {

    private String id;
    private String telefone;
    private String nome;

}
