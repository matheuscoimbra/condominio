package com.br.condomio.apt.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangeBetweenDTO implements Serializable {

    private String blocoId;
    private String apartementoIdTo;
    private String getApartementoIdFrom;
}
