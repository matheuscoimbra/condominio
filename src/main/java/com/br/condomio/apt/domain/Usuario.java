package com.br.condomio.apt.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotEmpty( message = "Informe o cpf")
    public  String cpf;
}
