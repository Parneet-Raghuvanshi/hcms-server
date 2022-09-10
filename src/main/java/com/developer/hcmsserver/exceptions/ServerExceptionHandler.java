package com.developer.hcmsserver.exceptions;

import com.developer.hcmsserver.models.GeneralResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ServerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServerException.class})
    public ResponseEntity<Object> handleUserServiceException(ServerException exception, WebRequest request) {
        GeneralResponse response = new GeneralResponse(
                true,
                exception.getException(),
                exception.getMessage(),
                null);
        return buildResponseEntity(response,exception.getStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleMainException(Exception exception, WebRequest request) {
        GeneralResponse response = new GeneralResponse(
                true,
                "UNDEFINED",
                exception.getMessage(),
                null);
        return buildResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildResponseEntity(GeneralResponse response,HttpStatus httpStatus) {
        return new ResponseEntity<>(response, httpStatus);
    }
}
