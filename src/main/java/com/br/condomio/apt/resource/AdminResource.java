package com.br.condomio.apt.resource;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.domain.VerificationToken;
import com.br.condomio.apt.dto.AdminDTO;
import com.br.condomio.apt.dto.CredenciaisDTO;
import com.br.condomio.apt.dto.TokenDTO;
import com.br.condomio.apt.event.OnRegistrationCompleteEvent;
import com.br.condomio.apt.jwt.JwtService;
import com.br.condomio.apt.service.AdminService;
import com.br.condomio.apt.service.exception.SenhaInvalidaException;
import com.br.condomio.apt.service.exception.UserAlreadyExistException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Optional;

@RestController
@RequestMapping("admin")
public class AdminResource {

    @Autowired
    private AdminService service;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ModelMapper mapper;


    @PostMapping()
    public ResponseEntity<String> autInquilino(@RequestBody @Valid AdminDTO adm, BindingResult result, HttpServletRequest request){

        var admin = mapper.map(adm,Admin.class);
        admin.cpf = adm.getCpf();

        var exist = service.findByCPF(admin.cpf);
        if(exist!=null) {
            throw new UserAlreadyExistException("usuário já cadastrado");
        }
        String baseUrl = String.format("%s://%s:%d/admin",request.getScheme(),  request.getServerName(), request.getServerPort());

        String senhaCriptografada = passwordEncoder.encode(admin.getSenha());
        admin.setSenha(senhaCriptografada);
        Admin registeredUser = service.save(admin);

        try {
            String appUrl = baseUrl;
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale(),appUrl));
        }catch(Exception re) {
            re.printStackTrace();
//			throw new Exception("Error while sending confirmation email");
        }

        return ResponseEntity.created(null).body("Verifique a caixa de entrada do seu email");
    }


    @GetMapping("/confirmRegistration")
    public String confirmRegistration(WebRequest request, @RequestParam("token") String token) {
        Optional<VerificationToken> verificationToken = service.getVerificationToken(token);
        if(verificationToken.isPresent()==false) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sem autorização");

        }
        Admin user = verificationToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if((verificationToken.get().getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Expirado autorização");

        }

        user.setEnabled(true);
        service.enableRegisteredUser(user);
        return "confirmation";
    }

    @PostMapping("/login")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{

            var user = service.findByCPF(credenciais.getCpf());
            if(!user.isEnabled()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário desabilitado");
            }

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
