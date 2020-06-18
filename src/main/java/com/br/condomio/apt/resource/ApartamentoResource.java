package com.br.condomio.apt.resource;


import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Aprovacao;
import com.br.condomio.apt.domain.Notificacao;
import com.br.condomio.apt.domain.Propriedade;
import com.br.condomio.apt.dto.*;
import com.br.condomio.apt.service.ApartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Retorna todos apartamentos por bloco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "retorna apartamentos"
                   ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping("all/bloco")
    public ResponseEntity<List<ApartamentoDTO>> getAllByBloco(@Parameter(description = "id do bloco") @RequestParam("id") String id){

       return ResponseEntity.ok(service.getAllByBloco(id));
    }

    @PostMapping("change/between")
    public ResponseEntity<List<ApartamentoDTO>> changeBetween(@RequestBody ChangeBetweenDTO changeBetweenDTO){
        return ResponseEntity.ok(service.changeBetWeen(changeBetweenDTO));
    }

    @Operation(summary = "Retorna apartamento por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "retorna apartamento"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Apartamento> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.findById(id));
    }
    @Operation(summary = "muda nome do apartamento por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "retorna apartamento"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping(value = "change/{id}")
    public ResponseEntity<?> changeName(@PathVariable("id") String id, @RequestParam("name") String name){
        service.changeName(id,name);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "remove inquilino do apartamento por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inquilino removido"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('SINDICO','ADMIN')")
    @DeleteMapping(value = "{id}/morador")
    public ResponseEntity<?> deleteInquilino(@Parameter(description = "id do apartamento") @PathVariable("id") String id){
        service.removeInquilino(id);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "remove apartamento por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "apartamento removido"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "notifica apartamento por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "notificação feita"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PostMapping(value = "{id}/notify")
    public ResponseEntity<?> addInquilino(@PathVariable("id")String id,@RequestBody NotificacaoDTO notificacaoDTO){
        service.notifyInquilino(id,notificacaoDTO);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "retorna notificações de inquilino por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "notificações retornadas"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping(value = "notify")
    public ResponseEntity<List<Notificacao>> addInquilino(@Parameter(description = "id do inquilino") @RequestParam("inquilino")String inquilino){
        return ResponseEntity.ok( service.notifies(inquilino));
    }

    @Operation(summary = "adiciona inquilino em apartamento por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inquilino solicitação"
            ),
            @ApiResponse(responseCode = "422", description = "apartamento já possui inquilino"
            ),
            @ApiResponse(responseCode = "412", description = "apartamento já possui solicitação"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PostMapping(value = "{id}/morador")
    public ResponseEntity<?> addMorador(@PathVariable("id")String id,@RequestBody InquilinoDTO inquilinoDTO){
        service.saveInquilino(id,inquilinoDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "adiciona convidado em apartamento por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "convidado solicitação"
            ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PostMapping(value = "{id}/convidado")
    public ResponseEntity<?> addConvidado(@PathVariable("id")String id,@RequestBody ConvidadoDTO convidadoDTO){
        service.addConvidado(id,convidadoDTO);
        return ResponseEntity.ok().build();
    }

}
