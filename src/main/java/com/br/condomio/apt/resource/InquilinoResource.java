package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Convidado;
import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.service.InquilinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("morador")
public class InquilinoResource {

    @Autowired
    private InquilinoService service;

    @PostMapping
    public ResponseEntity<Inquilino> save(@RequestBody InquilinoDTO inquilino){
        return ResponseEntity.created(null).body(service.save(inquilino));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Inquilino> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("verifica")
    public ResponseEntity<Inquilino> save(@RequestParam("telefone") String telefone){
        return ResponseEntity.ok(service.find(telefone));
    }


    @GetMapping("/{id}/convidados")
    public ResponseEntity<List<Convidado>> getAllConvidados(@PathVariable("id") String id){
        return ResponseEntity.ok(service.getAllConvidados(id));
    }

    @DeleteMapping("{moradorId}/convidado/{id}")
    public ResponseEntity<?> deleteConvidado(@PathVariable("moradorId") String inquiniloId, @PathVariable("id") String id){
        service.deleteConvidado(inquiniloId,id);
        return ResponseEntity.noContent().build();
    }
}
