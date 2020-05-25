package com.br.condomio.apt.resource;


import com.br.condomio.apt.dto.ApartamentoDTO;
import com.br.condomio.apt.dto.ChangeBetweenDTO;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.dto.NotificacaoDTO;
import com.br.condomio.apt.service.ApartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PatchMapping("change/{id}")
    public ResponseEntity<?> changeName(@PathVariable("id") String id, @RequestParam("name") String name){
        service.changeName(id,name);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/inquilino")
    public ResponseEntity<?> deleteInquilino(@PathVariable("id") String id){
        service.removeInquilino(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/notify")
    public ResponseEntity<?> addInquilino(@PathVariable("id")String id,@RequestBody NotificacaoDTO notificacaoDTO){
        service.notifyInquilino(id,notificacaoDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/inquilino")
    public ResponseEntity<?> addInquilino(@PathVariable("id")String id,@RequestBody InquilinoDTO inquilinoDTO){
        service.saveInquilino(id,inquilinoDTO);
        return ResponseEntity.ok().build();
    }

}
