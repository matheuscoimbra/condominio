package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.StatusInquilino;
import com.br.condomio.apt.domain.enums.TurnoPorteiro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "porteiro")
public class Porteiro {

    @Id
    private String id;
    private String telefone;
    private String cpf;
    private String nome;
    private String condominioId;
    Map<TurnoPorteiro, List<Date>> turno;
    private Boolean porteiro = true;

}
