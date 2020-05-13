package com.br.condomio.apt.dto;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.domain.Notificacao;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BlocoDTO implements Serializable {

    private String id;

    private String nome;

   // private List<Apartamento> apartamentos;
}
