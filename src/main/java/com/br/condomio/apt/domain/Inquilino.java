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
import java.util.Map;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "inquilino")
public class Inquilino {

    @Id
    private String id;
    private String telefone;
    private String nome;
    private String token;
    @DBRef
    private List<Convidado> convidados;
    @DBRef
    private List<Aprovacao> aprovacaos =  new ArrayList<>();
    private List<ApartamentoProp> propriedades = new ArrayList<>();
    private List<InquilinoSituacao> inquilinoSituacaos = new ArrayList<>();
    private boolean inquilinoPropietario;


}
