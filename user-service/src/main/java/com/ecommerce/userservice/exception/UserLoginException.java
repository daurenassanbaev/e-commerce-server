package com.ecommerce.userservice.exception;

public class UserLoginException extends RuntimeException {
    public UserLoginException(String message) {
        super(message);
    }
}