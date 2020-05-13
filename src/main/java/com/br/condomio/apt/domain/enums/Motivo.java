package com.br.condomio.apt.domain.enums;

public enum Motivo {
    BARULHO(1, "BARULHO"), FESTA(2, "FESTA");

    private int cod;
    private String description;


    Motivo(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return description;
    }

    public static Motivo toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Motivo x : Motivo.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
