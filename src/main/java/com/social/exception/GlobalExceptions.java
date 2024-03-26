package com.social.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptions {
	
     @ExceptionHandler(Exception.class) 
	public ResponseEntity<ErrorDetails> OtherExceptionHandler(Exception exception, WebRequest rq){
		
		ErrorDetails error = new ErrorDetails(exception.getMessage(),rq.getDescription(false), LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.BAD_REQUEST);
	}
     
     @ExceptionHandler(UserException.class) 
 	public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException exception, WebRequest rq){
 		
 		ErrorDetails error = new ErrorDetails(exception.getMessage(),rq.getDescription(false), LocalDateTime.now());
 		
 		return new ResponseEntity<ErrorDetails>(error,HttpStatus.BAD_REQUEST);
 	}
}
