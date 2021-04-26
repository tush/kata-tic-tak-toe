package com.tush.kata.game.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ControllerExceptionHandler
 * The ControllerAdvice class to handle API exceptions
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Log log = LogFactory.getLog(getClass());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception ex) {
        log.error("An error occurred processing request " + ex);
        return ex.getMessage();
    }
}
