package com.ecommerce.common.exception;


public class AlreadyActivatedException extends RuntimeException {

    public AlreadyActivatedException(String message) {
        super(message);
    }
}