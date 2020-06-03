package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Convidado;
import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.dto.ConvidadoDTO;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.service.ConvidadoService;
import com.br.condomio.apt.service.InquilinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("convidado")
public class ConvidadoResource {

    @Autowired
    private ConvidadoService service;

    @PostMapping
    public ResponseEntity<Convidado> save(@RequestBody ConvidadoDTO convidado){
        return ResponseEntity.created(null).body(service.save(convidado));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Convidado> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("verifica")
    public ResponseEntity<Convidado> save(@RequestParam("telefone") String telefone){
        return ResponseEntity.ok(service.find(telefone));
    }
}
