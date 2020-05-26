package com.br.condomio.apt.domain.enums;

public enum ObjetivoInquilino {
    MORADOR(1, "MORADOR"), VISITANTE(2, "VISITANTE");

    private int cod;
    private String description;


    ObjetivoInquilino(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return description;
    }

    public static ObjetivoInquilino toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (ObjetivoInquilino x : ObjetivoInquilino.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
