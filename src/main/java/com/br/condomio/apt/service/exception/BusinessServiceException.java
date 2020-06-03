package com.br.condomio.apt.service.exception;

public class BusinessServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessServiceException(String msg) {
        super(msg);
    }

    public BusinessServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
