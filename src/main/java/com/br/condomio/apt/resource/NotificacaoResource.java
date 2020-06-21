package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Notificacao;
import com.br.condomio.apt.dto.NotificacaoDTO;
import com.br.condomio.apt.dto.NotificacoesDTO;
import com.br.condomio.apt.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("notificacao")
public class NotificacaoResource {

    @Autowired
    private NotificacaoService service;

    @Operation(summary = "notifica inquilino por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "notificação feita"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PostMapping(value = "{id}/notify")
    public ResponseEntity<?> addInquilinoNotificacao(@PathVariable("id")String id, @Valid @RequestBody NotificacaoDTO notificacaoDTO){
        service.notifyInquilino(id,notificacaoDTO);
        return ResponseEntity.created(null).build();
    }


    @Operation(summary = "retorna notificações de inquilino por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "notificações retornadas"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping(value = "notify")
    public ResponseEntity<NotificacoesDTO> notificacoes(@Parameter(description = "id do inquilino") @RequestParam("inquilino")String inquilino){
        return ResponseEntity.ok( service.notifies(inquilino));
    }


    @Operation(summary = "retorna uma notificacao e atualiza para lida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "notificação retornada"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping(value = "notify/{id}")
    public ResponseEntity<Notificacao> notificacao(@Parameter(description = "id da notificacao") @PathVariable("id")String id){
        return ResponseEntity.ok( service.retornaPorId(id));
    }
}
