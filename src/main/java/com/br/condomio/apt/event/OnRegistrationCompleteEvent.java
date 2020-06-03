package com.br.condomio.apt.event;

import com.br.condomio.apt.domain.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;


public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;


    private String appUrl;
        private Locale locale;
        private Admin user;


    public OnRegistrationCompleteEvent(
            Admin user, Locale locale, String appUrl) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Admin getUser() {
        return user;
    }

    public void setUser(Admin user) {
        this.user = user;
    }
}


