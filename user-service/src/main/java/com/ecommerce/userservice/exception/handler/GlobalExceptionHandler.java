package com.ecommerce.userservice.exception.handler;

import com.ecommerce.userservice.exception.*;
import com.ecommerce.userservice.model.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnhandledExceptions(Exception e,
                                                                  WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                            WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ErrorResponseDto> handleUserRegistrationException(
            UserRegistrationException e,
            WebRequest request
    ) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<ErrorResponseDto> handleUserLoginException(
            UserLoginException e,
            WebRequest request
    ) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.UNAUTHORIZED,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AdminTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleAdminTokenException(AdminTokenException e, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.UNAUTHORIZED,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorResponseDto> handleTokenRefreshException(TokenRefreshException e, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.UNAUTHORIZED,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LogoutException.class)
    public ResponseEntity<ErrorResponseDto> handleLogoutException(LogoutException e, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoleAssignmentException.class)
    public ResponseEntity<ErrorResponseDto> handleRoleAssignmentException(RoleAssignmentException e, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRoleNotFoundException(RoleNotFoundException e, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsAlreadyArchivedException.class)
    public ResponseEntity<ErrorResponseDto> handleUserIsAlreadyArchivedException(UserIsAlreadyArchivedException e, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsAlreadyActivatedException.class)
    public ResponseEntity<ErrorResponseDto> handleRoleNotFoundException(UserIsAlreadyActivatedException e, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
