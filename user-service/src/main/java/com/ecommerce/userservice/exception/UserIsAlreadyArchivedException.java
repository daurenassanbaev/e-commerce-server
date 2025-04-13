package com.ecommerce.userservice.exception;

public class UserIsAlreadyArchivedException extends RuntimeException {

    public UserIsAlreadyArchivedException(String message) {
        super(message);
    }
}
