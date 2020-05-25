package com.br.condomio.apt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredenciaisDTO {
    private String cpf;
    private String senha;
    private String telefone;
}
