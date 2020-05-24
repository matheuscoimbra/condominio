package com.br.condomio.apt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "admin")
public class Admin {

    @NotEmpty( message = "Informe o nome")
    private String nome;
    @NotEmpty( message = "Informe o email")
    private String email;
    @NotEmpty( message = "Informe o cnpj")
    private String cnpj;
    private String token;
    private Date tokenValidade;
    @NotEmpty( message = "Informe a senha")
    private String senha;
    private List<String> condominiosId =  new ArrayList<>();
    private boolean admin = true;
}
