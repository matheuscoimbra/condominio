package com.br.condomio.apt.resource;


import com.br.condomio.apt.dto.ApartamentoDTO;
import com.br.condomio.apt.dto.BlocoDTO;
import com.br.condomio.apt.service.ApartamentoService;
import com.br.condomio.apt.service.BlocoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bloco")
public class BlocoResource {

    @Autowired
    private BlocoService service;

    @GetMapping()
    public ResponseEntity<List<BlocoDTO>> getAll(@RequestParam("id") String id){

       return ResponseEntity.ok(service.getAll(id));
    }

    @PatchMapping("change/{id}")
    public ResponseEntity<?> changeName(@PathVariable("id") String id, @RequestParam("name") String name){
        service.changeName(id,name);

        return ResponseEntity.noContent().build();
    }
}
