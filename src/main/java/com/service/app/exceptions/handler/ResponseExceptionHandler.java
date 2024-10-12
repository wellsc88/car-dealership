package com.service.app.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.service.app.exceptions.ResourceNotFoundException;
import com.service.app.exceptions.ValidationException;
import com.service.app.model.ExceptionResponse;

/**
 * Global exception handler for REST controllers.
 * 
 * <p>Provides centralized exception handling for all controllers in the application.</p>
 * 
 * @author Wellington
 * @version 1.0
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
     * Handles general exceptions.
     * 
     * @param ex the thrown exception.
     * @param request the web request where the exception occurred.
     * @return a {@link ResponseEntity} with the exception details and HTTP status.
     */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
     * Handles {@link ResourceNotFoundException}.
     * 
     * @param ex the thrown exception.
     * @param request the web request where the exception occurred.
     * @return a {@link ResponseEntity} with the exception details and HTTP status.
     */
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	/**
     * Handles {@link ValidationException}.
     * 
     * @param ex the thrown exception.
     * @param request the web request where the exception occurred.
     * @return a {@link ResponseEntity} with the exception details and HTTP status.
     */
	@ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(Exception ex, WebRequest request){
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
	
}
