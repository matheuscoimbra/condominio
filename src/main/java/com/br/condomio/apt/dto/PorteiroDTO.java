package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Usuario;
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
public class PorteiroDTO {

    private String telefone;
    private String nome;
    private String condominioId;
    Map<TurnoPorteiro, List<Date>> turno;

}
