package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.service.InquilinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("inquilino")
public class InquilinoResource {

    @Autowired
    private InquilinoService service;

    @PostMapping
    public ResponseEntity<Inquilino> save(@RequestBody Inquilino inquilino){
        return ResponseEntity.created(null).body(service.save(inquilino));
    }

    @GetMapping("verifica")
    public ResponseEntity<Inquilino> save(@RequestParam("telefone") String telefone){
        return ResponseEntity.ok(service.find(telefone));
    }
}
