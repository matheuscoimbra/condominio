package com.br.condomio.apt.domain.enums;

public enum PerfilInquilino {

    PROPRIETARIO(1, "PROPRIETARIO"), CONVIDADO(2, "CONVIDADO"),INQUILINO(3, "INQUILINO");

    private int cod;
    private String description;


    PerfilInquilino(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return description;
    }

    public static PerfilInquilino toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (PerfilInquilino x : PerfilInquilino.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
