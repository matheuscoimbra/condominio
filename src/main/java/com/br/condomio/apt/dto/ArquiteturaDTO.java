package com.br.condomio.apt.dto;

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

    private List<BlocoDTO> blocos;

    @JsonIgnoreProperties({"notificacaos"})
    private List<ApartamentoDTO> salas;
    @JsonIgnoreProperties({"notificacaos"})
    private List<ApartamentoDTO> casas;


    private Boolean proximaEtapa;

}
