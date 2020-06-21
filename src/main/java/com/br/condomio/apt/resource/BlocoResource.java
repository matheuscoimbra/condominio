package com.br.condomio.apt.resource;


import com.br.condomio.apt.domain.Convidado;
import com.br.condomio.apt.dto.ApartamentoDTO;
import com.br.condomio.apt.dto.ArquiteturaDTO;
import com.br.condomio.apt.dto.BlocoDTO;
import com.br.condomio.apt.service.ApartamentoService;
import com.br.condomio.apt.service.BlocoService;
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
@RequestMapping("bloco")
public class BlocoResource {

    @Autowired
    private BlocoService service;

    @Operation(summary = "retorna todos os blocos de determinada propriedade (condominio)/ Ou salas (prédio) ou Casas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "blocos retornados",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Convidado.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping()
    public ResponseEntity<ArquiteturaDTO> getAll(@Parameter(description = "id da propriedade") @RequestParam("condominio") String id){

       return ResponseEntity.ok(service.getAll(id));
    }








    @Operation(summary = "muda nome do bloco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "nome alterado"
                    ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('SINDICO','ADMIN')")
    @PatchMapping("change/{id}")
    public ResponseEntity<?> changeName(@PathVariable("id") String id, @RequestParam("name") String name){
        service.changeName(id,name);

        return ResponseEntity.noContent().build();
    }
}
