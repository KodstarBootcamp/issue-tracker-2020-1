package com.kodstar.backend.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void invalidArgument(HttpServletRequest request, Exception e) {
        log.info("invalid argument: {}", request.getRequestURL().toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict(HttpServletRequest request,DataIntegrityViolationException e){
        log.info("Data integrity violation: {}", request.getRequestURL().toString(), e.getMessage());
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
