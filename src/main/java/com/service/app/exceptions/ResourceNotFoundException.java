package com.service.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for handling resource not found scenarios.
 * 
 * @author Wellington
 * @version 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a new ResourceNotFoundException with the given message.
     * 
     * @param message the detail message.
     */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
