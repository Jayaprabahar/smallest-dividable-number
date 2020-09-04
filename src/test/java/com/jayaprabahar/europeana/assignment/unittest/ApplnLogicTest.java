/**
 * 
 */
package com.jayaprabahar.europeana.assignment.unittest;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.jayaprabahar.europeana.assignment.logic.SmallestDividableNumberCalculator;

/**
 * <p> Project : smallestdividablenumber </p>
 * <p> Title : ApplnLogicTest.java </p>
 * <p> Description: Unit testing core business logic</p>
 * <p> Created: Jun 30, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */

public class ApplnLogicTest {

	/**
	 * Tests correctness of the output  
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/test_data.csv", numLinesToSkip = 1)
	void testLCMWithinTheRange(int upperLimit, BigInteger result) {
		assertEquals(result, new SmallestDividableNumberCalculator().getSmallestDividableNumberWithinRange(upperLimit));
	}

}
