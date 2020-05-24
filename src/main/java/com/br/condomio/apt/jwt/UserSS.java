package com.br.condomio.apt.jwt;


import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class UserSS implements Serializable {

	private String username;
	private String password;
	private List<Map<Object,Object>> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

}
