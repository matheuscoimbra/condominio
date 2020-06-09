package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Usuario;
import com.br.condomio.apt.domain.enums.TurnoPorteiro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PorteiroDTO {
    @CPF(message = "Informe um cpf válido")
    private String cpf;
    @NotBlank(message = "Informe o telefone")
    private String telefone;
    @NotBlank(message = "Informe o nome")
    private String nome;
    Map<TurnoPorteiro, List<Date>> turno;

}
