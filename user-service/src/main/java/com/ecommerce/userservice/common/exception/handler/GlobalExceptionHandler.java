package com.ecommerce.userservice.common.exception.handler;

import com.ecommerce.common.model.dto.ErrorResponseDto;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.util.ErrorResponseUtil;
import com.ecommerce.userservice.auth.exception.*;
import com.ecommerce.common.exception.AlreadyActivatedException;
import com.ecommerce.common.exception.AlreadyArchivedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnhandledExceptions(Exception e, WebRequest webRequest) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, webRequest, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, webRequest, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorResponseDto> handleUserRegistrationException(RegistrationException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponseDto> handleUserLoginException(LoginException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AdminTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleAdminTokenException(AdminTokenException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorResponseDto> handleTokenRefreshException(TokenRefreshException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LogoutException.class)
    public ResponseEntity<ErrorResponseDto> handleLogoutException(LogoutException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoleAssignmentException.class)
    public ResponseEntity<ErrorResponseDto> handleRoleAssignmentException(RoleAssignmentException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRoleNotFoundException(RoleNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyArchivedException.class)
    public ResponseEntity<ErrorResponseDto> handleUserIsAlreadyArchivedException(AlreadyArchivedException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyActivatedException.class)
    public ResponseEntity<ErrorResponseDto> handleRoleNotFoundException(AlreadyActivatedException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}