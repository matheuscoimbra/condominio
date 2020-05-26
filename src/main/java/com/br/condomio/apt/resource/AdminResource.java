package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.dto.CredenciaisDTO;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.dto.TokenDTO;
import com.br.condomio.apt.jwt.JwtService;
import com.br.condomio.apt.service.AdminService;
import com.br.condomio.apt.service.exception.SenhaInvalidaException;
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
@RequestMapping("admin")
public class AdminResource {

    @Autowired
    private AdminService service;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @PostMapping()
    public ResponseEntity<Admin> autInquilino(@RequestBody Admin admin){
        String senhaCriptografada = passwordEncoder.encode(admin.getSenha());
        admin.setSenha(senhaCriptografada);
        admin.cpf = admin.getCpf();
        return ResponseEntity.created(null).body(service.save(admin));
    }

    @PostMapping("/login")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Admin usuario = Admin.builder()
                    .senha(credenciais.getSenha()).build();
            usuario.cpf = credenciais.getCpf();
            UserDetails usuarioAutenticado = service.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(service.findByCPF(credenciais.getCpf()), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
