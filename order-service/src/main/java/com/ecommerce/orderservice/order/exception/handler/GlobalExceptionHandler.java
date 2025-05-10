package com.ecommerce.orderservice.order.exception.handler;

import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.orderservice.order.exception.EmptyOrderItemException;
import com.ecommerce.orderservice.order.exception.InventoryReserveException;
import com.ecommerce.orderservice.order.exception.OrderCreationException;
import com.ecommerce.common.model.dto.ErrorResponseDto;
import com.ecommerce.common.util.ErrorResponseUtil;
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyOrderItemException.class)
    public ResponseEntity<ErrorResponseDto> handleEmptyOrderItemException(EmptyOrderItemException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InventoryReserveException.class)
    public ResponseEntity<ErrorResponseDto> handleInventoryReserveException(InventoryReserveException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCreationException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderCreationException(OrderCreationException e, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}