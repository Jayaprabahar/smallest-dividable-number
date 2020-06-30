/**
 * 
 */
package com.jayaprabahar.europeana.assignment.integrationtest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayaprabahar.europeana.assignment.errorhandling.UpperLimitNotSetException;
import com.jayaprabahar.europeana.assignment.errorhandling.UpperLimitReachedException;

/**
 * <p> Project : smallestdividablenumber </p>
 * <p> Title : ApplnIntegrationTest.java </p>
 * <p> Description: Integration Test class. Tests at controller level or at API level to test how the how application reacts to the call</p>
 * <p> Created: Jun 29, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ApplnIntegrationTest {

	private static final String API_URL = "/europeana/smallestdividablenumber";

	@Autowired
	private MockMvc mockMvc;

	@Value("#{new Integer('${europeana.assignment.smallestdividablenumbercalculator.allowedMaxUpperLimit}')}")
	public Integer allowedMaxUpperLimit;

	/* @formatter:off */
	
	@Test
	public void givenNoUpperLimit_thenStatus400_UpperLimitNotPresent() throws Exception {
		String response = mockMvc.perform(post(API_URL))
				.andExpect(status().isBadRequest())
				.andReturn().getResolvedException().getMessage();
		assertEquals("Required Integer parameter 'upperLimit' is not present", response);
	}

	@Test
	public void givenNonIntegerUpperLimit_thenStatus400_NotIntegerError() throws Exception {
		Exception response = mockMvc.perform(post(API_URL)
				.param("upperLimit", "I_AM_NOT_A_NUMBER"))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResolvedException();
		assertTrue(response.getCause() instanceof NumberFormatException);
		assertThat(response.getMessage(), containsString("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'"));
	}

	@Test
	public void givenNegativeNumberUpperLimit_thenStatus400_OnlyPositiveNumbersAllowedError() throws Exception {
		Exception response = mockMvc.perform(post(API_URL)
				.param("upperLimit", "-12"))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResolvedException();
		assertTrue(response instanceof ConstraintViolationException);
		assertThat(response.getMessage(), containsString("Only positive numbers are allowed"));
	}

	@Test
	public void givenBeyondRangePositiveUpperLimit_thenStatus400_BeyondAllowedUpperLimitError() throws Exception {
		Exception response = mockMvc.perform(post(API_URL)
				.param("upperLimit", "1212"))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResolvedException();
		assertTrue(response instanceof UpperLimitReachedException);
		assertThat(response.getMessage(), containsString("Allowed upper limit is " + allowedMaxUpperLimit));
	}

	@Test
	public void givenPositiveCorrectRangeUpperLimit_thenStatus201() throws Exception {
		mockMvc.perform(post(API_URL).param("upperLimit", "12"))
		.andExpect(status().isCreated())
		.andReturn();
	}

	@Test
	public void givenXMLAcceptHeaderWithPositiveCorrectRangeUpperLimit_thenNoChangeInResponse() throws Exception {
		mockMvc.perform(post(API_URL).accept(MediaType.APPLICATION_XML)
				.param("upperLimit", "12"))
		.andExpect(status().isCreated());
	}

	@Test
	public void givenCorrectRangeUpperLimitSet_GetApiCall_thenSuccessResponse() throws Exception {
		HttpSession session = mockMvc.perform(post(API_URL)
				.param("upperLimit", "12"))
				.andExpect(status().isCreated())
				.andReturn().getRequest()
				.getSession();

		MvcResult response = mockMvc.perform(get(API_URL)
				.session((MockHttpSession) session))
				.andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.upperLimit", is(12)))
		        .andExpect(jsonPath("$.smallestdividablenumber", is(27720)))
		        .andExpect(jsonPath("$.timeTakenInMillis", isA(Integer.class)))
		        .andExpect(jsonPath("$.timeTakenInMillis", notNullValue())).andReturn();
		assertThat(response.getResponse().getContentAsString(), containsString("{\"upperLimit\":12,\"smallestdividablenumber\":27720,\"timeTakenInMillis\":"));
	}
	
	@Test
	public void givenCorrectRangeUpperLimitSet_GetApiCallWithXMLAcceptHeader_thenSuccessResponse() throws Exception {
		HttpSession session = mockMvc.perform(post(API_URL)
				.param("upperLimit", "20"))
				.andExpect(status().isCreated())
				.andReturn().getRequest()
				.getSession();

		MvcResult response = mockMvc.perform(get(API_URL).accept(MediaType.APPLICATION_XML)
				.session((MockHttpSession) session))
				.andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_XML))
		        .andExpect(xpath("Response/upperLimit").number(20d))
		        .andExpect(xpath("Response/smallestdividablenumber").number(232792560d))
		        .andExpect(xpath("Response/timeTakenInMillis").exists())
		        .andReturn();
		assertThat(response.getResponse().getContentAsString(), containsString("<Response><upperLimit>20</upperLimit><smallestdividablenumber>232792560</smallestdividablenumber><timeTakenInMillis>"));
	}
	
	@Test
	public void givenUpperLimitNotSet_GetApiCall_thenPreconditionFailedError() throws Exception {
		MvcResult postResponse = mockMvc.perform(post(API_URL).param("upperLimit", "1212"))
				.andExpect(status().isBadRequest()).andReturn();
		HttpSession session = postResponse.getRequest().getSession();

		Exception getResponse = mockMvc.perform(get(API_URL).session((MockHttpSession) session))
				.andExpect(status().isPreconditionFailed())
				.andReturn()
				.getResolvedException();
		assertTrue(getResponse instanceof UpperLimitNotSetException);
		assertThat(getResponse.getMessage(), containsString("Upper limit is not set"));
	}

	@Test
	public void givenCorrectRangeUpperLimitSet_GetApiCallWithNotAllowedAcceptHeader_thenStatus406() throws Exception {
		HttpSession session = mockMvc.perform(post(API_URL)
				.param("upperLimit", "20"))
				.andExpect(status().isCreated())
				.andReturn().getRequest()
				.getSession();
		
		mockMvc.perform(get(API_URL)
				.accept(MediaType.APPLICATION_PDF).session((MockHttpSession) session))
		.andExpect(status().isNotAcceptable());
	}

	/* @formatter:on */

}
