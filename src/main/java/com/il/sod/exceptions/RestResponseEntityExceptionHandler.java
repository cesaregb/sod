package com.il.sod.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.il.sod.vo.ErrorVo;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
    	ErrorVo error = new ErrorVo(ErrorVo.GENERAL_ERROR, ex.getMessage());
    	return handleExceptionInternal(ex, error, 
    			new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleRTEConflict(RuntimeException ex, WebRequest request) {
    	ErrorVo error = new ErrorVo(ErrorVo.GENERAL_ERROR, ex.getMessage());
    	return handleExceptionInternal(ex, error, 
    			new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    @ExceptionHandler(value = { SODException.class })
    protected ResponseEntity<Object> handleConflictSOD(SODException ex, WebRequest request) {
    	ErrorVo error = new ErrorVo(ErrorVo.GENERAL_ERROR, ex.getMessage());
    	return handleExceptionInternal(ex, error, 
    			new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                HttpHeaders headers, HttpStatus status, WebRequest request){
    	ErrorVo error = new ErrorVo(ErrorVo.GENERAL_ERROR, "404 no resource found");
    	return handleExceptionInternal(ex, error, 
    			new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}