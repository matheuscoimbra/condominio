package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.domain.Aprovacao;
import com.br.condomio.apt.domain.Propriedade;
import com.br.condomio.apt.domain.Sindico;
import com.br.condomio.apt.dto.CredenciaisDTO;
import com.br.condomio.apt.dto.SindicoDTO;
import com.br.condomio.apt.dto.TokenDTO;
import com.br.condomio.apt.jwt.JwtService;
import com.br.condomio.apt.service.AdminService;
import com.br.condomio.apt.service.SindicoService;
import com.br.condomio.apt.service.exception.SenhaInvalidaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("sindico")
public class SindicoResource {
    @Autowired
    private SindicoService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;


    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Cria um novo síndico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "síndico criado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SindicoDTO.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
                })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<SindicoDTO> addSindico(@RequestBody SindicoDTO dto){
        Sindico sindico = mapper.map(dto, Sindico.class);
        sindico.cpf = dto.getCpf();
        return ResponseEntity.created(null).body(service.save(sindico));
    }

    @Operation(summary = "Retorna síndico por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "síndico retornado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sindico.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Sindico> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Retorna aprovações de síndico por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "aprovações retornadas"),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('SINDICO')")
    @GetMapping(value = "/{id}/aprovacao")
    public ResponseEntity<List<Aprovacao>> buscaAprovacoesPorId(@Parameter(description = "id do síndico")  @PathVariable("id") String id){

        return ResponseEntity.ok(service.buscaAprovacoesPorId(id));
    }



    @Operation(summary = "Login sindico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login efetuado"
                  ),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PostMapping("/login")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{

            UserDetails usuarioAutenticado = service.autenticar(credenciais);
            Sindico sindico = service.findByTelefone(credenciais.getTelefone());
            String token = jwtService.gerarTokenSindico(sindico);
            return new TokenDTO(service.getById(sindico.getId()), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
