package com.br.condomio.apt.domain.enums;

public enum TurnoPorteiro {

    MATUTINO(1, "MATUTINO"), VESPERTINO(2, "VESPERTINO"), NOTURNO(3,"NOTURNO");

    private int cod;
    private String description;


    TurnoPorteiro(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return description;
    }

    public static TurnoPorteiro toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (TurnoPorteiro x : TurnoPorteiro.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
