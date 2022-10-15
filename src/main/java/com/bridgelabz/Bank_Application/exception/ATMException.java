package com.bridgelabz.Bank_Application.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

public @Data class ATMException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public ATMException(HttpStatus httpStatus, String message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
    }
    public ATMException(String message) {
        super(message);
    }
}
