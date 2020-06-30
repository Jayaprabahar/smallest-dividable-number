/**
 * 
 */
package com.jayaprabahar.europeana.assignment.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p> Project : smallestdividablenumber </p>
 * <p> Title : UpperLimitNotSetException.java </p>
 * <p> Description: Can be used to throw exception when there is no passed upper limit value set</p>
 * <p> Created: Jun 29, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class UpperLimitNotSetException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8717268944600512140L;

	/**
	 * 
	 */
	public UpperLimitNotSetException() {
		super("Upper limit is not set");
	}

}
