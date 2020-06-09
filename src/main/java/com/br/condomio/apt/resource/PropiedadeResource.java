package com.br.condomio.apt.resource;


import com.br.condomio.apt.domain.Propriedade;
import com.br.condomio.apt.dto.BlocoDTO;
import com.br.condomio.apt.dto.PredioDTO;
import com.br.condomio.apt.service.PropriedadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("propriedade")
public class PropiedadeResource {

    @Autowired
    private PropriedadeService service;
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("predio")
    public ResponseEntity<Propriedade> predio(@RequestBody @Valid PredioDTO condominio){

       return ResponseEntity.ok(service.savePredio(condominio));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("blocos")
    public ResponseEntity<Propriedade> blocos(@RequestBody @Valid BlocoDTO condominio){

        return ResponseEntity.ok(service.save(condominio));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("sindico")
    public ResponseEntity<?> saveSindico(@RequestParam("sindico_id") String sindico, @RequestParam("propriedade_id") String propriedade){
        service.saveSindicoPropriedade(sindico,propriedade);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("porteiro")
    public ResponseEntity<?> savePorteiro(@RequestParam("porteiro_id") String porteiro, @RequestParam("propriedade_id") String propriedade){
        service.savePorteiroPropriedade(porteiro,propriedade);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Propriedade> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/propietario")
    public ResponseEntity<List<Propriedade>> getAll(@RequestParam("cpf") String cnpj){

        return ResponseEntity.ok(service.getAllByPropietario(cnpj));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Propriedade>> getAllByName(@RequestParam(value = "nome", required = false) String nome){

        return ResponseEntity.ok(service.getAllByNome(nome));
    }

    @GetMapping("/inquilino/todos")
    public ResponseEntity<List<Propriedade>> getAllByNameSindico(@RequestParam(value = "nome", required = false) String nome){

        return ResponseEntity.ok(service.getNomeSindicoNotNull(nome));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("change/{id}")
    public ResponseEntity<?> changeName(@PathVariable("id") String id, @RequestParam("name") String name){
        service.changeName(id,name);

        return ResponseEntity.noContent().build();
    }
}
