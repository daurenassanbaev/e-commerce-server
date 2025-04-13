package com.ecommerce.userservice.exception;


public class UserIsAlreadyActivatedException extends RuntimeException {

    public UserIsAlreadyActivatedException(String message) {
        super(message);
    }
}