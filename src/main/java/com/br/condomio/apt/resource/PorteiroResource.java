package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Sindico;
import com.br.condomio.apt.dto.CredenciaisDTO;
import com.br.condomio.apt.dto.PorteiroDTO;
import com.br.condomio.apt.dto.SindicoDTO;
import com.br.condomio.apt.dto.TokenDTO;
import com.br.condomio.apt.jwt.JwtService;
import com.br.condomio.apt.service.PorteiroService;
import com.br.condomio.apt.service.SindicoService;
import com.br.condomio.apt.service.exception.SenhaInvalidaException;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("porteiro")
public class PorteiroResource {
    @Autowired
    private PorteiroService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;


    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "salva porteiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "porteiro salvo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PorteiroDTO.class))}),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<PorteiroDTO> addPorteiro(@RequestBody PorteiroDTO dto){

        return ResponseEntity.created(null).body(service.save(dto));
    }
    @Operation(summary = "busca porteiro por id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PorteiroDTO> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "login porteiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "porteiro salvo"),
            @ApiResponse(responseCode = "500", description = "gerou exceção",
                    content = @Content),
    })
    @PostMapping("/login")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{

            UserDetails usuarioAutenticado = service.autenticar(credenciais);
            Sindico sindico = service.findByTelefone(credenciais.getTelefone());
            String token = jwtService.gerarTokenPorteiro(sindico);
            return new TokenDTO(service.getById(sindico.getId()), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
