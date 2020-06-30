package com.jayaprabahar.europeana.assignment.controller;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jayaprabahar.europeana.assignment.errorhandling.UpperLimitNotSetException;
import com.jayaprabahar.europeana.assignment.errorhandling.UpperLimitReachedException;
import com.jayaprabahar.europeana.assignment.model.Response;
import com.jayaprabahar.europeana.assignment.service.ApplnService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> Project : smallestdividablenumber </p>
 * <p> Title : ApplnController.java </p>
 * <p> Description: Controller class for API call</p>
 * <p> Created: Jun 29, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@RestController
@RequestMapping("/europeana/smallestdividablenumber")
@Validated
@Slf4j
public class ApplnController {

	public static final String SESSION_ATTR_UPPERLIMIT = "UPPER_LIMIT";

	// Fetch the allowed maximum upper limit value from the application properties.
	@Value("#{new Integer('${europeana.assignment.smallestdividablenumbercalculator.allowedMaxUpperLimit}')}")
	public Integer allowedMaxUpperLimit;

	private ApplnService applnService;

	public ApplnController(ApplnService applnService) {
		this.applnService = applnService;
	}

	/**
	 * This method will be called during POST Api call for loading/updating UPPER_LIMIT in the session
	 * 
	 * @param upperLimit - Positive integer whose value must be in the range 1 to allowedMaxUpperLimit
	 * @param request - HttpServletRequest to load/update the session value
	 */
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(code = HttpStatus.CREATED)
	public void setUpperLimit(@Valid @Positive(message = "Only positive numbers are allowed") @RequestParam("upperLimit") Integer upperLimit, HttpServletRequest request) {
		if (upperLimit > allowedMaxUpperLimit) {
			throw new UpperLimitReachedException(allowedMaxUpperLimit);
		}

		request.getSession().setAttribute(SESSION_ATTR_UPPERLIMIT, upperLimit);
		log.info("Set {} as upper limit", upperLimit);
	}

	/**
	 * This method will be called during Get Api call for calculating the smallest dividable number of all number from the positive upper limit. 
	 * Upper limit value is read from session variable UPPER_LIMIT
	 * 
	 * @param session - HttpSession to read the UPPER_LIMIT value from session
	 * @return Response - Response model with values are upperLimit, smallest dividable number and timeTaken In Milliseconds
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Response getSmallestDividableNumber(HttpSession session) {
		Object maxNumber = session.getAttribute(SESSION_ATTR_UPPERLIMIT);

		if (!(maxNumber instanceof Integer && (int) maxNumber > 0)) {
			throw new UpperLimitNotSetException();
		}
		long startTime = System.currentTimeMillis();
		
		BigInteger result = applnService.getSmallestDividableNumberWithinRange((int) maxNumber);
		
		log.info("Smallest Dividable Number within the positive number from 1 to {} is {}", maxNumber, result);

		return Response.builder().upperLimit((int) maxNumber).smallestdividablenumber(applnService.getSmallestDividableNumberWithinRange((int) maxNumber))
		        .timeTakenInMillis(System.currentTimeMillis() - startTime).build();
	}

}
