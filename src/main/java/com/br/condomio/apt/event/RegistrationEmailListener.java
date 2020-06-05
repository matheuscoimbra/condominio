package com.br.condomio.apt.event;

import com.br.condomio.apt.domain.Admin;
import com.br.condomio.apt.dto.Mail;
import com.br.condomio.apt.service.AdminService;
import com.br.condomio.apt.service.SindicoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private AdminService userService;

    @Autowired
    private JavaMailSender emailSender;


    @Autowired
    private SpringTemplateEngine templateEngine;

    @SneakyThrows
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);

    }

    @SneakyThrows
    private void confirmRegistration(OnRegistrationCompleteEvent event) throws IOException {
        Admin user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String recipient = user.getEmail();
        String subject = "Confirmação de registro";
        String url
                = event.getAppUrl() + "/confirmRegistration?token=" + token;
        log.info(event.getAppUrl());
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Mail mail = new Mail();
        mail.setMailTo(recipient);//replace with your desired email
        mail.setSubject(subject);
        mail.setFrom("mathcoimbr4@gmail.com");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("token", token);
        model.put("url", url);
        mail.setProps(model);

        Context context = new Context();
        context.setVariables(mail.getProps());
        log.info("enviado email....",url);
        String html = templateEngine.process("email", context);

        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom("mathcoimbr4@gmail.com");



        emailSender.send(message);



    }

}