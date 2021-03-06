package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.Arquitetura;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "propriedade")
public class Propriedade implements Serializable {

    @Id
    private String id;

    @NotEmpty(message ="Informe o CNPJ")
    private String cnpj;

    @NotEmpty(message = "Informe o nome do condomínio")
    private String nome;

    private SindicoProp sindico;

    @NotEmpty(message = "Informe o nome do propietário")
    private String propietario;

    private PorteiroProp porteiro;

    @NotNull(message = "Informe a arquitetura")
    private Arquitetura arquitetura;

    @JsonIgnore
    @DBRef
    private List<Bloco> blocos;

    @NotNull(message = "Informe a quantidade de BLOCO/PREDIO")
    private Integer quantidadeArquitetura;

    @NotNull(message = "Informe a quantidade de andares por BLOCO/PREDIO")
    private Integer quantidadeAndar;

    @NotNull(message = "Informe a quantidade de apartamentos por BLOCO/PREDIO")
    private Integer quantidadeApartamento;

    private String start;

    private String intervalo;

    private String bairro;

    private String cidade;

    private String uf;

    private String cep;

    private String numero;

    private String endereco;

    private String complemento;

    private String latitude;

    private String longitude;

    private boolean comSindico;

    private boolean comPorteiro;

    private Integer buscados;




}
