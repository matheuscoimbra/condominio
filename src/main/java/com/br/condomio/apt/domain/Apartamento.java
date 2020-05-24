package com.br.condomio.apt.domain;

import com.br.condomio.apt.dto.InquilinoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "apartamento")
public class Apartamento {

    @Id
    private String id;

    private String nome;

    private Integer andar;

    private String condomioCnpj;

    private InquilinoDTO inquilino;

    private List<Notificacao> notificacaos = new ArrayList<>();


}
