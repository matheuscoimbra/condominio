package com.br.condomio.apt.config;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log
@Component
public class ScheduleHerokuConfig {

    @Scheduled(cron = "0 0/30 * 1/1 * ?")
    public void sendNotify(){
        log.info("teste");
    }

}
