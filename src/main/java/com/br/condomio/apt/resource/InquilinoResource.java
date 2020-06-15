package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Apartamento;
import com.br.condomio.apt.domain.Convidado;
import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.dto.TokenDTO;
import com.br.condomio.apt.service.InquilinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("morador")
public class InquilinoResource {

    @Autowired
    private InquilinoService service;

    @Operation(summary = "salva inquilino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "inquilino salvo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inquilino.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PostMapping
    public ResponseEntity<Inquilino> save(@RequestBody InquilinoDTO inquilino){
        return ResponseEntity.created(null).body(service.save(inquilino));
    }
    @Operation(summary = "busca por id inquilino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inquilino retornado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inquilino.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Inquilino> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "verifica existencia de inquilino pelo numero de telefone (string)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inquilino retornado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inquilino.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping("verifica")
    public ResponseEntity<Inquilino> save(@RequestParam("telefone") String telefone){
        return ResponseEntity.ok(service.find(telefone));
    }

    @Operation(summary = "retorna convidados de um inquilino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inquilino retornado"),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping("/{id}/convidados")
    public ResponseEntity<List<Convidado>> getAllConvidados(@Parameter(description = "id do inquilino")  @PathVariable("id") String id){
        return ResponseEntity.ok(service.getAllConvidados(id));
    }
    @Operation(summary = "deleta convidado de um inquilino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "convidado deletado"),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @DeleteMapping("{moradorId}/convidado/{id}")
    public ResponseEntity<?> deleteConvidado(@Parameter(description = "id do inquilino") @PathVariable("moradorId") String inquiniloId,@Parameter(description = "id do convidado")  @PathVariable("id") String id){
        service.deleteConvidado(inquiniloId,id);
        return ResponseEntity.noContent().build();
    }
}
