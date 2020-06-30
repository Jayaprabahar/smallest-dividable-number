/**
 * 
 */
package com.jayaprabahar.europeana.assignment.errorhandling;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p> Project : smallestdividablenumber </p>
 * <p> Title : ApplnControllerAdvice.java </p>
 * <p> Description: Controller Advice class for unhandled exception</p>
 * <p> Created: Jun 29, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@RestControllerAdvice
public class ApplnControllerAdvice {

	/**
	 * Added for overriding HTTP 500 status code as 400 for ConstraintViolation exception 
	 * 
	 * @param e - ConstraintViolationException
	 * @param response - HttpServletResponse
	 * @throws IOException - Rethrow status code overridden exception  
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	void handleConstraintViolationException(ConstraintViolationException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
}