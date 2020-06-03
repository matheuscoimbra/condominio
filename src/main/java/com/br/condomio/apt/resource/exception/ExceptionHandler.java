package com.br.condomio.apt.resource.exception;

import com.br.condomio.apt.service.exception.BusinessServiceException;
import com.br.condomio.apt.service.exception.InvalidPhoneNumberException;
import com.br.condomio.apt.service.exception.ObjectNotFoundException;
import com.br.condomio.apt.service.exception.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketTimeoutException;


@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(InvalidPhoneNumberException.class)
	public ResponseEntity<StandardError> handlerAuthExcetion(InvalidPhoneNumberException e, HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Informe um número de telefone válido", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(UnprocessableEntityException.class)
	public ResponseEntity<StandardError> unprocessableEntityException(UnprocessableEntityException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Não no processamento", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}



	@org.springframework.web.bind.annotation.ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> badRequestException(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}



	@org.springframework.web.bind.annotation.ExceptionHandler(SocketTimeoutException.class)
	public ResponseEntity<StandardError> socketTimeoutException(SocketTimeoutException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.REQUEST_TIMEOUT.value(), "Timeout", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(err);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(BusinessServiceException.class)
	public ResponseEntity<StandardError> businessServiceException(BusinessServiceException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.PRECONDITION_FAILED.value(), "Integridade de dados", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(err);
	}
}
