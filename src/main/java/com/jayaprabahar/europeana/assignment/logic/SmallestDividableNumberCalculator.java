package com.jayaprabahar.europeana.assignment.logic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> Project : smallestdividablenumber </p>
 * <p> Title : SmallestDividableNumberCalculator.java </p>
 * <p> Description: Spring bean implementation for calculating Smallest dividable Number in the max given range. This implementation is written based on unique 
 * prime factorization theorem </p>
 * <p> Created: Jun 29, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@Component
@Slf4j
public class SmallestDividableNumberCalculator {

	/**
	 * Returns the smallest dividable number within the given maxNumber
	 * 
	 * @param maxNumber - The Maximum number within which the range to be calculated
	 * @return BigInteger  Smallest dividable number within the given maxNumber
	 */
	public BigInteger getSmallestDividableNumberWithinRange(int maxNumber) {
		log.debug("Calculate Smallest Dividable Number for {}", maxNumber);

		return getPrimeNumberListWithInRange(maxNumber).stream().map(value -> value.pow(getMaxPowerInRange(value.intValue(), maxNumber))).reduce(BigInteger.ONE,
		        BigInteger::multiply);
	}

	/**
	 * Returns the list of prime numbers as BigIntegers in the given max range
	 * 
	 * @param maxNumber - The Maximum number within which the range to be calculated
	 * @return List<BigInteger> - List of prime numbers within the given range
	 */
	public List<BigInteger> getPrimeNumberListWithInRange(int maxNumber) {
		List<BigInteger> primeNumberList = new ArrayList<>();

		BigInteger max = BigInteger.valueOf(maxNumber);
		BigInteger probable = BigInteger.valueOf(1);

		while (probable.compareTo(max) <= 0) {
			primeNumberList.add(probable);
			probable = probable.nextProbablePrime();
		}
		log.debug("PrimeNumber list within {} is {}", maxNumber, primeNumberList);
		return primeNumberList;
	}

	/**
	 * Returns the max power of powerOf number within the range of maxNumber
	 * 
	 * @param powerOf - The number for which the power of to be calcualted
	 * @param maxNumber - The Maximum number within which the range to be calculated
	 * @return int - Maximum allowed power of value within the range
	 */
	public int getMaxPowerInRange(int powerOf, int maxNumber) {
		return (int) (Math.log(maxNumber) / Math.log(powerOf));
	}

}
