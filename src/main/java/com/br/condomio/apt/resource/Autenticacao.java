package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.service.InquilinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("autenticacao")
public class Autenticacao {

    @Autowired
    private InquilinoService service;

    @GetMapping("inquilino")
    public ResponseEntity<Inquilino> autInquilino(@RequestParam("telefone") String telefone){
        return ResponseEntity.ok(service.find(telefone));
    }


}
