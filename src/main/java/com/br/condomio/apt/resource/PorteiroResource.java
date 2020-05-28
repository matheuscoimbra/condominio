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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping()
    public ResponseEntity<PorteiroDTO> autInquilino(@RequestBody PorteiroDTO dto){

        return ResponseEntity.created(null).body(service.save(dto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PorteiroDTO> buscaPorId(@PathVariable("id") String id){

        return ResponseEntity.ok(service.getById(id));
    }

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
