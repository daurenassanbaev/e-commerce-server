package com.ecommerce.reviewservice.exception.handler;

import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.model.dto.ErrorResponseDto;
import com.ecommerce.reviewservice.exception.DuplicateReviewException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateReviewException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateReviewException(DuplicateReviewException e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
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
