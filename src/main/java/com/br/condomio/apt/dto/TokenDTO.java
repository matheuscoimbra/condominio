package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Sindico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private Object usuario;
    private String token;
}
