package com.br.condomio.apt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CondomioAptApplication {

    public static void main(String[] args) {
        SpringApplication.run(CondomioAptApplication.class, args);
    }

}
