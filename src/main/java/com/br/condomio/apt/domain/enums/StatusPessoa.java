package com.br.condomio.apt.domain.enums;

public enum StatusPessoa {
    ANALISE(1, "ANALISE"), APROVADO(2, "APROVADO"),  RECUSADO(3, "RECUSADO");

    private int cod;
    private String description;


    StatusPessoa(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return description;
    }

    public static StatusPessoa toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (StatusPessoa x : StatusPessoa.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
