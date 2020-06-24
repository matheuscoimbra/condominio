package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Bloco;
import com.br.condomio.apt.domain.enums.Arquitetura;
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

    private List<LugarArquiteturaDTO> arquiteturas;



    private Arquitetura tipo;
    private Boolean temProximaEtapa;

}
