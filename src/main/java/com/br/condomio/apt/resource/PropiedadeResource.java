package com.br.condomio.apt.resource;


import com.br.condomio.apt.domain.Propriedade;
import com.br.condomio.apt.dto.BlocoDTO;
import com.br.condomio.apt.dto.PredioDTO;
import com.br.condomio.apt.dto.SindicoDTO;
import com.br.condomio.apt.service.PropriedadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Cria propriedade do tipo prédio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "propriedade criada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propriedade.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("predio")
    public ResponseEntity<Propriedade> predio(@RequestBody @Valid PredioDTO condominio){

       return ResponseEntity.ok(service.savePredio(condominio));
    }
    @Operation(summary = "Cria propriedade do tipo blocos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "propriedade criada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propriedade.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("blocos")
    public ResponseEntity<Propriedade> blocos(@RequestBody @Valid BlocoDTO condominio){

        return ResponseEntity.ok(service.save(condominio));
    }
    @Operation(summary = "salva sindico em determinada propriedade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "salva sindico em determinada propriedade"
                 ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("sindico")
    public ResponseEntity<?> saveSindico(@RequestParam("sindico_id") String sindico, @RequestParam("propriedade_id") String propriedade){
        service.saveSindicoPropriedade(sindico,propriedade);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "salva porteiro em determinada propriedade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "salva porteiro em determinada propriedade"
                  ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("porteiro")
    public ResponseEntity<?> savePorteiro(@RequestParam("porteiro_id") String porteiro, @RequestParam("propriedade_id") String propriedade){
        service.savePorteiroPropriedade(porteiro,propriedade);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "retorna propriedade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "retorna propriedade",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propriedade.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Propriedade> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.getById(id));
    }
    @Operation(summary = "retorna lista de propriedades de determinado admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "retorna propriedade"
                 ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping("/propietario")
    public ResponseEntity<List<Propriedade>> getAll(@RequestParam("cpf") String cnpj){

        return ResponseEntity.ok(service.getAllByPropietario(cnpj));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Propriedade>> getAllByName(@RequestParam(value = "nome", required = false) String nome,@RequestParam(value = "cidade", required = false) String cidade){

        return ResponseEntity.ok(service.getAllByNome(nome,cidade));
    }

    @Operation(summary = "retorna lista de propriedades pelo nome que possuam sindico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "retorna propriedade"
                   ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping("/inquilino/todos")
    public ResponseEntity<List<Propriedade>> getAllByNameSindico(@RequestParam(value = "nome", required = false) String nome,@RequestParam(value = "cidade", required = false) String cidade){

        return ResponseEntity.ok(service.getNomeSindicoNotNull(nome,cidade));
    }

    @Operation(summary = "muda nome da propriedade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "muda nome propriedade"
                   ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("change/{id}")
    public ResponseEntity<?> changeName(@PathVariable("id") String id, @RequestParam("name") String name){
        service.changeName(id,name);

        return ResponseEntity.noContent().build();
    }
}
