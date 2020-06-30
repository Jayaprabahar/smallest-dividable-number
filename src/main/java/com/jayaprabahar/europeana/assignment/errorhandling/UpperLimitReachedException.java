/**
 * 
 */
package com.jayaprabahar.europeana.assignment.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p> Project : smallestdividablenumber </p>
 * <p> Title : UpperLimitReachedException.java </p>
 * <p> Description: Can be used to throw exception when the passed upper limit value is greater than allowed maximum value</p>
 * <p> Created: Jun 29, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UpperLimitReachedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6814205330668367255L;

	/**
	 * @param allowedUpperLimit
	 */
	public UpperLimitReachedException(int allowedUpperLimit) {
		super("Allowed upper limit is " + allowedUpperLimit);
	}

}
