package io.github.javaasasecondlanguage.flitter.util;

import io.github.javaasasecondlanguage.flitter.controller.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> wrongRequest(HttpServletRequest req, NoHandlerFoundException e) throws Exception {
        return BaseResponse.error("Bad request");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> applicationErrorHandler(HttpServletRequest req, NotFoundException e) throws Exception {
        return BaseResponse.notFound(e.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> defaultErrorHandler(HttpServletRequest req, AlreadyExistsException e) throws Exception {
        return BaseResponse.alreadyExists(e.getMessage());
    }
}
