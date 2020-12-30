package com.kodstar.backend.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void notFound(HttpServletRequest request, Exception e){
        log.info("not found: {} - {}: {}", request.getRequestURL().toString(), e.getClass().getSimpleName(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void badRequest(HttpServletRequest request, Exception e) {
        log.info("illegal argument: {}", request.getRequestURL().toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void httpMessageNotReadable(HttpServletRequest request, Exception e) {
        log.info("invalid format: {}", request.getRequestURL().toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void invalidFormat(HttpServletRequest request, Exception e) {
        log.info("invalid format: {}", request.getRequestURL().toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Error validationFail(HttpServletRequest request, ConstraintViolationException e){
        String errorMessage = "validation rule broken";
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation v: e.getConstraintViolations()) {
            log.info(errorMessage + ": {} - {}", request.getRequestURL().toString(), v.getMessageTemplate());
            errors.add(v.getMessageTemplate());
        }

        return new Error(errorMessage,errors);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void anyException(HttpServletRequest request, Exception e) {
        log.warn("something went wrong: {}", request.getRequestURL().toString(), e);
    }

    @Getter
    private class Error{

        private String errorMessage;
        private List<String> errorFields = new ArrayList<>();

        private Error(String errorMessage, List errorFields){
            this.errorMessage = errorMessage;
            this.errorFields = errorFields;
        }
    }
}
