package com.br.condomio.apt.domain;

import com.br.condomio.apt.domain.enums.StatusInquilino;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "inquilino")
public class Inquilino {

    @Id
    private String id;
    private String telefone;
    private String nome;
    private String token;
    private List<Map<String,StatusInquilino>> statusInquilino;
    private List<Map<String,String>> apartamentosCondList; //condominioid /apartamentoid


}
