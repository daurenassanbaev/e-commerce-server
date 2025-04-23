package com.ecommerce.userservice.auth.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}