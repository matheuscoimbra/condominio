package com.br.condomio.apt.domain;


import com.br.condomio.apt.domain.enums.ObjetivoInquilino;
import com.br.condomio.apt.domain.enums.StatusInquilino;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "aprovacao")
public class Aprovacao {

    @Id
    private String id;

    private String inquilinoNome;
    private String telefone;
    private String inquilinoId;
    private String apartamentoId;
    private String propriedadeId;
    private StatusInquilino statusInquilino;
    private ObjetivoInquilino objetivoInquilino;

}
