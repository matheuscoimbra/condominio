package com.br.condomio.apt.resource;


import com.br.condomio.apt.domain.Condominio;
import com.br.condomio.apt.dto.BlocoDTO;
import com.br.condomio.apt.service.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("condominio")
public class CondominioResource {

    @Autowired
    private CondominioService service;

    @PostMapping
    public ResponseEntity<Condominio> save(@RequestBody @Valid Condominio condominio){

       return ResponseEntity.ok(service.save(condominio));
    }

    @GetMapping("/propietario")
    public ResponseEntity<List<Condominio>> getAll(@RequestParam("cnpj") String cnpj){

        return ResponseEntity.ok(service.getAllByPropietario(cnpj));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Condominio>> getAllByName(@RequestParam("nome") String cnpj){

        return ResponseEntity.ok(service.getAllByNome(cnpj));
    }

    @PatchMapping("change/{id}")
    public ResponseEntity<?> changeName(@PathVariable("id") String id, @RequestParam("name") String name){
        service.changeName(id,name);

        return ResponseEntity.noContent().build();
    }
}
