package com.br.condomio.apt.domain.enums;

public enum Arquitetura {

    BLOCO(1, "BLOCO"), PREDIO(2, "PREDIO"),CASA(1, "CASA");

    private int cod;
    private String description;


    Arquitetura(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return description;
    }

    public static Arquitetura toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Arquitetura x : Arquitetura.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
