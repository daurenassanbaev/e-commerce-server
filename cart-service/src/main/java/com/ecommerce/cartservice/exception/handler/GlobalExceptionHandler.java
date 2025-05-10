package com.ecommerce.cartservice.exception.handler;

import com.ecommerce.cartservice.exception.CartIsEmptyException;
import com.ecommerce.cartservice.exception.CheckoutFailedException;
import com.ecommerce.cartservice.exception.InvalidCartItemException;
import com.ecommerce.common.exception.AlreadyArchivedException;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.model.dto.ErrorResponseDto;
import com.ecommerce.common.util.ErrorResponseUtil;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnhandledExceptions(Exception e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CartIsEmptyException.class)
    public ResponseEntity<ErrorResponseDto> handleCartIsEmptyException(CartIsEmptyException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckoutFailedException.class)
    public ResponseEntity<ErrorResponseDto> handleCheckoutFailedException(CheckoutFailedException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidCartItemException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCartItemException(InvalidCartItemException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyArchivedException.class)
    public ResponseEntity<ErrorResponseDto> handleAlreadyArchivedException(AlreadyArchivedException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.CONFLICT), HttpStatus.CONFLICT); // 409
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponseDto> handleFeignException(FeignException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.BAD_GATEWAY), HttpStatus.BAD_GATEWAY); // 502
    }
}
