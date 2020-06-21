package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Notificacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacoesDTO implements Serializable {

    private String inquilino;

    private List<Notificacao> semana;

    private List<Notificacao> mes;

    private Integer total;
}
