package com.br.condomio.apt.domain.enums;

public enum StatusInquilino {
    ANALISE(1, "ANALISE"), APROVADO(2, "APROVADO"),  RECUSADO(3, "RECUSADO");

    private int cod;
    private String description;


    StatusInquilino(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return description;
    }

    public static StatusInquilino toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (StatusInquilino x : StatusInquilino.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
