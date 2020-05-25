package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.domain.Sindico;
import com.br.condomio.apt.dto.CredenciaisDTO;
import com.br.condomio.apt.dto.SindicoDTO;
import com.br.condomio.apt.dto.TokenDTO;
import com.br.condomio.apt.jwt.JwtService;
import com.br.condomio.apt.service.AdminService;
import com.br.condomio.apt.service.SindicoService;
import com.br.condomio.apt.service.exception.SenhaInvalidaException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping()
    public ResponseEntity<SindicoDTO> autInquilino(@RequestBody SindicoDTO dto){
        Sindico sindico = mapper.map(dto, Sindico.class);
        sindico.cpf = dto.getCpf();
        return ResponseEntity.created(null).body(service.save(sindico));
    }

    @PostMapping("/login")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{

            UserDetails usuarioAutenticado = service.autenticar(credenciais);
            Sindico sindico = service.findByTelefone(credenciais.getTelefone());
            String token = jwtService.gerarTokenSindico(sindico);
            return new TokenDTO(sindico.getTelefone(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
