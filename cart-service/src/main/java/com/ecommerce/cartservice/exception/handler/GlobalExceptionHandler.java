package com.ecommerce.cartservice.exception.handler;

import com.ecommerce.cartservice.exception.CartIsEmptyException;
import com.ecommerce.cartservice.exception.CheckoutFailedException;
import com.ecommerce.cartservice.exception.InvalidCartItemException;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.model.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnhandledExceptions(Exception e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CartIsEmptyException.class)
    public ResponseEntity<ErrorResponseDto> handleCartIsEmptyException(CartIsEmptyException e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckoutFailedException.class)
    public ResponseEntity<ErrorResponseDto> handleCheckoutFailedException(CheckoutFailedException e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidCartItemException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCartItemException(InvalidCartItemException e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(Exception e, WebRequest request, HttpStatus status) {
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                status,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }
}
