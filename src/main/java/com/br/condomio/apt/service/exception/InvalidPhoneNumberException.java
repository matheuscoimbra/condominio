package com.br.condomio.apt.service.exception;


@SuppressWarnings("serial")
public class InvalidPhoneNumberException extends RuntimeException {
	
	public InvalidPhoneNumberException() { }

	public InvalidPhoneNumberException(String message) {
		super(message);
	}

}
