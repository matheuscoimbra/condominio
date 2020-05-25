package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.domain.Sindico;
import com.br.condomio.apt.dto.CredenciaisDTO;
import com.br.condomio.apt.dto.SindicoDTO;
import com.br.condomio.apt.repository.SindicoRepository;
import com.br.condomio.apt.service.exception.SenhaInvalidaException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SindicoService {

    @Autowired
    private SindicoRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CloudinaryService service;

    public SindicoDTO save(Sindico s){

        s.setFoto(service.uploadFile(s.getFoto()));
        var sindico =  mapper.map(repository.save(s), SindicoDTO.class);
        sindico.setCpf(s.cpf);
        return sindico;

    }


    public UserDetails autenticar(CredenciaisDTO usuario ){
        UserDetails user = loadUserByTelefone(usuario.getTelefone());


        if(user != null){
            return user;
        }

        throw new SenhaInvalidaException();
    }

    public Sindico findByTelefone(String telefone){
        return repository.findSindicosByTelefone(telefone)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));
    }

    public Sindico findByCpf(String cpf){
        return repository.findSindicosByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));
    }

    public UserDetails loadUserByCpf(String cpf) {
        Sindico usuario = repository.findSindicosByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = usuario.isSindico() ?
                new String[]{"SINDICO"} : new String[]{"SINDICO"};

        return User
                .builder()
                .username(usuario.getTelefone())
                .password(usuario.getTelefone())
                .roles(roles)
                .build();
    }

    public UserDetails loadUserByTelefone(String telefone) throws UsernameNotFoundException {
        Sindico usuario = repository.findSindicosByTelefone(telefone)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = usuario.isSindico() ?
                new String[]{"SINDICO"} : new String[]{"SINDICO"};

        return User
                .builder()
                .username(usuario.getTelefone())
                .password(usuario.getTelefone())
                .roles(roles)
                .build();
    }

}
