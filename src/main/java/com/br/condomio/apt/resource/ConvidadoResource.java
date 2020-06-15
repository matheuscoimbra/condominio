package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Convidado;
import com.br.condomio.apt.domain.Inquilino;
import com.br.condomio.apt.dto.ConvidadoDTO;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.dto.PorteiroDTO;
import com.br.condomio.apt.service.ConvidadoService;
import com.br.condomio.apt.service.InquilinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("convidado")
public class ConvidadoResource {

    @Autowired
    private ConvidadoService service;

    @Operation(summary = "salva Convidado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Convidado salvo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Convidado.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PostMapping
    public ResponseEntity<Convidado> save(@RequestBody ConvidadoDTO convidado){
        return ResponseEntity.created(null).body(service.save(convidado));
    }
    @Operation(summary = "busca por id Convidado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convidado retornado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Convidado.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Convidado> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.findById(id));
    }
    @Operation(summary = "verifica convidado pelo numero telefone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convidado retornado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Convidado.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping("verifica")
    public ResponseEntity<Convidado> save(@RequestParam("telefone") String telefone){
        return ResponseEntity.ok(service.find(telefone));
    }
}
