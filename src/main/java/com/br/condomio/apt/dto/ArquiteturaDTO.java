package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Bloco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArquiteturaDTO implements Serializable {

    private List<Bloco> blocos;

    @JsonIgnoreProperties({"notificacaos"})
    private List<ApartamentoRespDTO> salas;
    @JsonIgnoreProperties({"notificacaos"})
    private List<ApartamentoRespDTO> casas;


    private Boolean proximaEtapa;

}
