package com.ecommerce.common.exception;

public class AlreadyArchivedException extends RuntimeException {

    public AlreadyArchivedException(String message) {
        super(message);
    }
}
