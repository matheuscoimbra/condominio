package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.ObjetivoInquilino;
import com.br.condomio.apt.domain.enums.StatusInquilino;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InquilinoSituacao {

    private String apartamentoId;
    private String condominio;
    private String apartamentoNome;
    private String blocoId;
    private String condominioId;
    private StatusInquilino statusInquilino;
    private ObjetivoInquilino objetivoInquilino;
}
