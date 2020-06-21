package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Propriedade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropriedadeSearchDTO {

    private List<Propriedade> propriedades = new ArrayList<>();

    private List<Propriedade> maisProcurados = new ArrayList<>();

}
