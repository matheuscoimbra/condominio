package com.br.condomio.apt.resource;


import com.br.condomio.apt.domain.Aprovacao;
import com.br.condomio.apt.domain.Convidado;
import com.br.condomio.apt.domain.enums.StatusPessoa;
import com.br.condomio.apt.service.AprovacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(summary = "Aprovação de inquilino por parte do síndico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "altera status aprovação",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aprovacao.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('SINDICO')")
    @PatchMapping("/sindico/inquilino")
    public ResponseEntity<Aprovacao> mudaStatus(@Parameter(description = "id da aprovação a ser alterada") @RequestParam("id") String aprovacaoId, @RequestParam("status") StatusPessoa status){
        service.mudaStatus(aprovacaoId,status);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Aprovação de convidado por parte do inquilino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "altera status aprovação",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aprovacao.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PatchMapping("/inquilino/convidado")
    public ResponseEntity<Aprovacao> mudaStatusConvidado(@Parameter(description = "id da aprovação a ser alterada") @RequestParam("id") String aprovacaoId,  @RequestParam("status") StatusPessoa status){
        service.mudaStatusConvidado(aprovacaoId,status);
        return ResponseEntity.ok().build();
    }
}
