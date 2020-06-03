package com.br.condomio.apt.service.exception;


@SuppressWarnings("serial")
public class UserAlreadyExistException extends RuntimeException {

	public UserAlreadyExistException() { }

	public UserAlreadyExistException(String message) {
		super(message);
	}

}
