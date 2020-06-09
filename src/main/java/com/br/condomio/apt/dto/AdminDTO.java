package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AdminDTO extends Usuario {

    @NotEmpty( message = "Informe o nome")
    private String nome;
    @CPF( message = "Informe um cpf válido")
    private String cpf;
    @NotEmpty( message = "Informe o email")
    private String email;
    @NotEmpty( message = "Informe a senha")
    private String senha;

}
