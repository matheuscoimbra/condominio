package com.br.condomio.apt.service;

import com.br.condomio.apt.jwt.UserSS;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;


public class UserService{

	public static UserSS authenticated() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			  var str  = mapper.writeValueAsString(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			 var obj = mapper.readValue(str,UserSS.class);
			 return obj;
		}
		catch (Exception e) {
			return null;
		}
	}
}
