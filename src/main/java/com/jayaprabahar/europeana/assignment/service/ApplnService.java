package com.jayaprabahar.europeana.assignment.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import com.jayaprabahar.europeana.assignment.logic.SmallestDividableNumberCalculator;

/**
 * <p> Project : smallestdividablenumber </p>
 * <p> Title : ApplnService.java </p>
 * <p> Description: Service bean</p>
 * <p> Created: Jun 29, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@Service
public class ApplnService {

	private SmallestDividableNumberCalculator calculator;

	public ApplnService(SmallestDividableNumberCalculator calculator) {
		this.calculator = calculator;
	}

	/**
	 * Calls business logic class to fetch the response for the given integer
	 * 
	 * @param maxNumber - The maximum number within which the calculation to be calcualted.
	 * @return BigInteger - Smallest dividable number within the maxNumber
	 */
	public BigInteger getSmallestDividableNumberWithinRange(int maxNumber) {
		return calculator.getSmallestDividableNumberWithinRange(maxNumber);
	}

}
