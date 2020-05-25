package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SindicoDTO {

    private String foto;
    private String cpf;
    private String telefone;
    private String nome;

}

