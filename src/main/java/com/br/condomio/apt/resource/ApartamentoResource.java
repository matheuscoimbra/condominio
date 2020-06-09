package com.br.condomio.apt.resource;


import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Propriedade;
import com.br.condomio.apt.dto.*;
import com.br.condomio.apt.service.ApartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("apartamento")
public class ApartamentoResource {

    @Autowired
    private ApartamentoService service;

    @GetMapping("all/bloco")
    public ResponseEntity<List<ApartamentoDTO>> getAllByBloco(@RequestParam("id") String id){

       return ResponseEntity.ok(service.getAllByBloco(id));
    }

    @PostMapping("change/between")
    public ResponseEntity<List<ApartamentoDTO>> changeBetween(@RequestBody ChangeBetweenDTO changeBetweenDTO){
        return ResponseEntity.ok(service.changeBetWeen(changeBetweenDTO));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Apartamento> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.findById(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping(value = "change/{id}")
    public ResponseEntity<?> changeName(@PathVariable("id") String id, @RequestParam("name") String name){
        service.changeName(id,name);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "{id}/morador")
    public ResponseEntity<?> deleteInquilino(@PathVariable("id") String id){
        service.removeInquilino(id);
        return ResponseEntity.ok().build();
    }



    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "{id}/notify")
    public ResponseEntity<?> addInquilino(@PathVariable("id")String id,@RequestBody NotificacaoDTO notificacaoDTO){
        service.notifyInquilino(id,notificacaoDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "{id}/morador")
    public ResponseEntity<?> addInquilino(@PathVariable("id")String id,@RequestBody InquilinoDTO inquilinoDTO){
        service.saveInquilino(id,inquilinoDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "{id}/convidado")
    public ResponseEntity<?> addConvidado(@PathVariable("id")String id,@RequestBody ConvidadoDTO convidadoDTO){
        service.addConvidado(id,convidadoDTO);
        return ResponseEntity.ok().build();
    }

}
