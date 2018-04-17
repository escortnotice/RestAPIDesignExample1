package com.rest.example.restcontroller.integrationtest;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rest.example.entity.Order;

/**
 * Launch the entire Spring Boot Application on a Random Port
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderIntegrationTest {

	/**
	 * Autowire the random port into the variable so that we can use it create the url.
	 */
		@LocalServerPort
		private int port;

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();

		@Test
		public void testGetOrder() throws JSONException {

			//We use entity so that we have the flexibility of adding in request headers in future.
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);

			//Fire a GET request to the specify uri and get the response as a String.
			ResponseEntity<String> response = restTemplate.exchange(
					createURLWithPort("/orders/1"),
					HttpMethod.GET, entity, String.class);

			String expectedOrder = "{\"order_Id\":1,\"name\":\"Chello Kabab\",\"price\":\"110.25\",\"quantity\":1}";

			//Assert the HTTP Status Code
			assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
			//Assert that the response contains expected fields.
			JSONAssert.assertEquals(expectedOrder, response.getBody(), false);
		}

		@Test
		@Transactional  //to rollback after inserting data to db
		public void testCreateOrder() throws Exception{
			
			Order order = new Order("Chicken Tikka", "125", 1);
			
			HttpEntity<Order> entity = new HttpEntity<Order>(order, headers);
			
			ResponseEntity<String> response = restTemplate.exchange(
					createURLWithPort("/orders"),
					HttpMethod.POST, entity, String.class);
			
			String expectedOrder = "{\"name\":\"Chicken Tikka\",\"price\":\"125\",\"quantity\":1}";
			
			assertEquals(HttpStatus.CREATED.value(),response.getStatusCodeValue());
			JSONAssert.assertEquals(expectedOrder, response.getBody(), false);
			
		}
		
		//Utility method to create the url given an uri. It appends the port.
		private String createURLWithPort(String uri) {
			return "http://localhost:" + port + uri;
		}

}
