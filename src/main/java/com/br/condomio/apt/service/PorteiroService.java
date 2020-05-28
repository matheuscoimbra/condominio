package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.Porteiro;
import com.br.condomio.apt.domain.Sindico;
import com.br.condomio.apt.dto.CredenciaisDTO;
import com.br.condomio.apt.dto.PorteiroDTO;
import com.br.condomio.apt.dto.SindicoDTO;
import com.br.condomio.apt.repository.PorteiroRepository;
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
public class PorteiroService {

    @Autowired
    private PorteiroRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CloudinaryService service;

    public PorteiroDTO save(PorteiroDTO s){
        var port  = mapper.map(s, Porteiro.class);
        port.cpf = s.getCpf();
        var porteiro =  mapper.map(repository.save(port), PorteiroDTO.class);
        porteiro.setCpf(porteiro.getCpf());
        return porteiro;

    }

    public PorteiroDTO getById(String id){
        return mapper.map(repository.findById(id).get(),PorteiroDTO.class);
    }


    public UserDetails autenticar(CredenciaisDTO usuario ){
        UserDetails user = loadUserByTelefone(usuario.getTelefone());


        if(user != null){
            return user;
        }

        throw new SenhaInvalidaException();
    }

    public Sindico findByTelefone(String telefone){
        return repository.findPorteiroByTelefone(telefone)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));
    }

    public Sindico findByCpf(String cpf){
        return repository.findPorteiroByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));
    }

    public UserDetails loadUserByCpf(String cpf) {
        Sindico usuario = repository.findPorteiroByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = usuario.isSindico() ?
                new String[]{"PORTEIRO"} : new String[]{"PORTEIRO"};

        return User
                .builder()
                .username(usuario.getTelefone())
                .password(usuario.getTelefone())
                .roles(roles)
                .build();
    }

    public UserDetails loadUserByTelefone(String telefone) throws UsernameNotFoundException {
        Sindico usuario = repository.findPorteiroByTelefone(telefone)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = usuario.isSindico() ?
                new String[]{"PORTEIRO"} : new String[]{"PORTEIRO"};

        return User
                .builder()
                .username(usuario.getTelefone())
                .password(usuario.getTelefone())
                .roles(roles)
                .build();
    }
}
