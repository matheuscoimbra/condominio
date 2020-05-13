package com.br.condomio.apt.resource;


import com.br.condomio.apt.dto.ApartamentoDTO;
import com.br.condomio.apt.service.ApartamentoService;
import com.br.condomio.apt.service.BlocoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bloco")
public class BlocoResource {

    @Autowired
    private BlocoService service;

    @GetMapping()
    public ResponseEntity<List<ApartamentoDTO>> getAll(@RequestParam("id") String id){

       return ResponseEntity.ok(service.getAll());
    }
}
