package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.dto.InquilinoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("autenticacao")
public class Autenticacao {

    @GetMapping("inquilino")
    public ResponseEntity<InquilinoDTO> autInquilino( @RequestParam("telefone") String telefone){
        return ResponseEntity.ok().build();
    }


}
