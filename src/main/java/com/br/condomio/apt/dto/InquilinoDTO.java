package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.enums.StatusInquilino;
import lombok.Data;

import java.io.Serializable;

@Data
public class InquilinoDTO implements Serializable {

    private String id;
    private String telefone;
    private String nome;
    private StatusInquilino statusInquilino;
}
