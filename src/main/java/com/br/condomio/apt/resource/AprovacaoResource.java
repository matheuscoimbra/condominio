package com.br.condomio.apt.resource;


import com.br.condomio.apt.domain.Aprovacao;
import com.br.condomio.apt.domain.enums.StatusInquilino;
import com.br.condomio.apt.service.AprovacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("aprovacao")
public class AprovacaoResource {

    @Autowired
    private AprovacaoService service;

    @PatchMapping()
    public ResponseEntity<Aprovacao> mudaStatus(@RequestParam("id") String aprovacaoId,  @RequestParam("status") StatusInquilino status){
        service.mudaStatus(aprovacaoId,status);
        return ResponseEntity.ok().build();
    }
}
