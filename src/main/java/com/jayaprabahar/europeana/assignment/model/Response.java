/**
 * 
 */
package com.jayaprabahar.europeana.assignment.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p> Project : smallestdividablenumber </p>
 * <p> Title : Response.java </p>
 * <p> Description: Response Model for API response</p>
 * <p> Created: Jun 29, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8696302080583261697L;

	private int upperLimit;
	private BigInteger smallestdividablenumber;
	private long timeTakenInMillis;

}
