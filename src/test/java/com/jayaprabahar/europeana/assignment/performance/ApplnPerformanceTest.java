/**
 * 
 */
package com.jayaprabahar.europeana.assignment.performance;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;

import javax.servlet.http.HttpSession;

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

import com.jayway.jsonpath.JsonPath;

/**
 * <p> Project : smallestdividablenumber </p>
 * <p> Title : ApplnPerformanceTest.java </p>
 * <p> Description: Tests performance of the calculator</p>
 * <p> Created: Jun 30, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ApplnPerformanceTest {

	private static final String API_URL = "/europeana/smallestdividablenumber";

	@Autowired
	private MockMvc mockMvc;

	@Value("#{new Integer('${europeana.assignment.smallestdividablenumbercalculator.allowedMaxUpperLimit}')}")
	public Integer allowedMaxUpperLimit;

	@Value("#{new Integer('${europeana.assignment.maximumAllowedTimeTaken.MilliSeconds}')}")
	public Integer allowedMaxTimeTaken;

	/* @formatter:off */

	@Test
	public void givenMultiplePostAndGetApiCalls_thenPerformanceShouldBeGood() throws Exception {
		Random randomNumber = new Random();
		
		// Try 50 calls
		for (int i = 0; i < 10; i++) {
			int passedUpperLimit = randomNumber.ints(1, allowedMaxUpperLimit).findFirst().getAsInt();
			HttpSession session = mockMvc.perform(post(API_URL)
					.param("upperLimit", String.valueOf(passedUpperLimit)))
					.andExpect(status().isCreated()).andReturn().getRequest()
					.getSession();
			MvcResult response = mockMvc.perform(get(API_URL)
					.session((MockHttpSession) session))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.upperLimit", is(passedUpperLimit)))
					.andExpect(jsonPath("$.timeTakenInMillis", isA(Integer.class)))
					.andExpect(jsonPath("$.timeTakenInMillis", notNullValue())).andReturn();
			
			assertEquals(1, Integer.compare(allowedMaxTimeTaken, JsonPath.parse(response.getResponse().getContentAsString()).read("$.timeTakenInMillis")));
		}
	}
	
	/* @formatter:on */

}
