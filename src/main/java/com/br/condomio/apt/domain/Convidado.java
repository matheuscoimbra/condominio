package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.PerfilInquilino;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "convidado")
public class Convidado {

    @Id
    private String id;
    private String telefone;
    private String nome;
    private List<ApartamentoProp> propriedades = new ArrayList<>();
    private List<InquilinoSituacao> convidadoSituacaos = new ArrayList<>();


}
