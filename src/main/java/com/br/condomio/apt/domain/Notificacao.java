package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.Motivo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "notificacao")
public class Notificacao implements Serializable {

    @Id
    private String id;

    private Motivo motivo;
    private String descricao;
    private Integer peso;
    private Date date;
    private Boolean lida;
    private String inquilino;
    private String apartamento;

    public Integer getPeso(){
        return motivo.getCod();
    }


}
