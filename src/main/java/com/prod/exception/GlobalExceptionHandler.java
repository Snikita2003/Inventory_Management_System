package com.prod.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prod.helper.Responce;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFounException.class)
    public ResponseEntity<Responce> handleNotFound(NotFounException ex) {
        Responce response = new Responce();
        response.setMsg(ex.getMessage());
        response.setStatus("Failed");
        response.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND );
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Responce> handleGeneric(Exception ex) {
        Responce response = new Responce();
        response.setMsg("Internal server error: " + ex.getMessage());
        response.setStatus("Failed");
        response.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}
