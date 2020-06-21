package com.br.condomio.apt.dto;

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

    private List<ApartamentoDTO> salas;

    private List<ApartamentoDTO> casas;


    private Boolean proximaEtapa;

}
