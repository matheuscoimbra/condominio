package com.br.condomio.apt.resource;


import com.br.condomio.apt.domain.Propriedade;
import com.br.condomio.apt.dto.BlocoDTO;
import com.br.condomio.apt.dto.PredioDTO;
import com.br.condomio.apt.service.PropriedadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("propriedade")
public class PropiedadeResource {

    @Autowired
    private PropriedadeService service;

    @PostMapping("predio")
    public ResponseEntity<Propriedade> predio(@RequestBody @Valid PredioDTO condominio){

       return ResponseEntity.ok(service.savePredio(condominio));
    }

    @PostMapping("blocos")
    public ResponseEntity<Propriedade> blocos(@RequestBody @Valid BlocoDTO condominio){

        return ResponseEntity.ok(service.save(condominio));
    }

    @GetMapping("/propietario")
    public ResponseEntity<List<Propriedade>> getAll(@RequestParam("cpf") String cnpj){

        return ResponseEntity.ok(service.getAllByPropietario(cnpj));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Propriedade>> getAllByName(@RequestParam("nome") String cnpj){

        return ResponseEntity.ok(service.getAllByNome(cnpj));
    }

    @PatchMapping("change/{id}")
    public ResponseEntity<?> changeName(@PathVariable("id") String id, @RequestParam("name") String name){
        service.changeName(id,name);

        return ResponseEntity.noContent().build();
    }
}
