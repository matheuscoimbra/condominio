package com.br.condomio.apt.service;

import com.br.condomio.apt.domain.*;
import com.br.condomio.apt.dto.ApartamentoDTO;
import com.br.condomio.apt.dto.ChangeBetweenDTO;
import com.br.condomio.apt.dto.InquilinoDTO;
import com.br.condomio.apt.dto.NotificacaoDTO;
import com.br.condomio.apt.repository.AdminRepository;
import com.br.condomio.apt.repository.ApartamentoRepository;
import com.br.condomio.apt.repository.BlocoRepository;
import com.br.condomio.apt.repository.VerificationRepository;
import com.br.condomio.apt.service.exception.SenhaInvalidaException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService implements UserDetailsService {
    
    @Autowired
    private BlocoRepository blocoRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private ApartamentoRepository apartamentoRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AdminRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public Admin findByCPF(String cpf){
         var result  = repository.findAdminByCpf(cpf);
         if(result.isPresent()){
             return result.get();
         }else{
             return null;
         }
    }

    public Admin save(Admin admin){
        return repository.save(admin);
    }


    public UserDetails autenticar( Admin usuario ){
        UserDetails user = loadUserByUsername(usuario.getCpf());
        boolean senhasBatem = encoder.matches( usuario.getSenha(), user.getPassword() );

        if(senhasBatem){
            return user;
        }

        throw new SenhaInvalidaException();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin usuario = repository.findAdminByCpf(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = usuario.isAdmin() ?
                new String[]{"ADMIN"} : new String[]{"ADMIN"};

        return User
                .builder()
                .username(usuario.getCpf())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
    boolean emailExist(String email) {
        return repository.findAdminByEmail(email).isPresent();
    }


    public void enableRegisteredUser(Admin user) {
        repository.save(user);
    }

    public void createVerificationToken(Admin user, String token) {
        VerificationToken newUserToken = new VerificationToken(token, user);
        verificationRepository.save(newUserToken);
    }
    public Optional<VerificationToken> getVerificationToken(String verificationToken) {
        return verificationRepository.findFirstByTokenEquals(verificationToken);
    }

}
