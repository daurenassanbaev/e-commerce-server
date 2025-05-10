package com.ecommerce.searchservice.common.exception.handler;

import com.ecommerce.common.model.dto.ErrorResponseDto;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.util.ErrorResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnhandledExceptions(Exception e, WebRequest webRequest) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, webRequest, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e, webRequest, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}
