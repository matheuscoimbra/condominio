package com.br.condomio.apt.domain.enums;

public enum Perfis {
    PROPRIETARIO(1, "PROPRIETARIO"), INQUILINO(2, "INQUILINO"), SINDICO(3,"SINDICO"), PORTEIRO(4,"PORTEIRO");

    private int cod;
    private String description;


    Perfis(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return description;
    }

    public static Perfis toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Perfis x : Perfis.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
