package com.ecommerce.common.util;

import com.ecommerce.common.model.dto.ErrorResponseDto;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@UtilityClass
public class ErrorResponseUtil {

    public static ErrorResponseDto buildErrorResponse(Exception e, WebRequest webRequest, HttpStatus status) {
        return new ErrorResponseDto(
                webRequest.getDescription(false),
                status,
                e.getMessage(),
                LocalDateTime.now()
        );
    }
}
