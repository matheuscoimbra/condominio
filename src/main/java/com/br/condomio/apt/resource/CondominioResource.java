package com.br.condomio.apt.resource;


import com.br.condomio.apt.domain.Condominio;
import com.br.condomio.apt.service.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("condominio")
public class CondominioResource {

    @Autowired
    private CondominioService service;

    @PostMapping
    public ResponseEntity<Condominio> save(@RequestBody Condominio condominio){

       return ResponseEntity.ok(service.save(condominio));
    }
}
