package com.ecommerce.userservice.auth.exception;

public class RoleAssignmentException extends RuntimeException {
    public RoleAssignmentException(String message) {
        super(message);
    }
}