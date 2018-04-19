package com.rest.example.restcontroller.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rest.example.customexceptions.DataNotFoundException;
import com.rest.example.entity.Order;
import com.rest.example.restcontroller.OrderRestController;
import com.rest.example.service.OrderService;


/**
 * Will load all the spring dependencies
 */
@RunWith(SpringRunner.class) 
/** 
 * @WebMvcTest annotation is used for unit testing Spring MVC application. 
 * This can be used when a test focuses only Spring MVC components. 
 * In this test, we want to launch only StudentController. 
 * All other controllers and mappings will not be launched when this unit test is executed.
 **/
@WebMvcTest(value = OrderRestController.class, secure = false)
public class OrderRestControllerTest {

	/**
	 * MockMvc is the main entry point for server-side Spring MVC test support. It
	 * allows us to execute requests against the test context.
	 */
	@Autowired
	MockMvc mockMVC;

	/**
	 * MockBean is used to add mocks to a Spring ApplicationContext. A mock of
	 * OrderService is created and auto-wired into the OrderRestController.
	 */
	@MockBean
	OrderService orderService;

	Order mockOrder = new Order(1l, "Chicken Tikka", "125", 1);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetOrder() throws Exception, DataNotFoundException {
		Mockito.when(orderService.getOrder(Mockito.anyLong())).thenReturn(mockOrder);

		/**
		 * Creating a Request builder to be able to execute a GET request to uri
		 * “orders/1” with accept header as “application/json”
		 */
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/1").accept(MediaType.APPLICATION_JSON);

		// mockMvc is used to trigger the request and return the response back.
		MvcResult result = mockMVC.perform(requestBuilder).andReturn();
		System.out.println("Result - " + result.getResponse().getContentAsString());

		String expectedJsonResponse = "{\"order_Id\":1,\"name\":\"Chicken Tikka\",\"price\":\"125\",\"quantity\":1}";

		// assert the response status codes
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		// We are passing strict as "false" so that we don't have to check for all
		// fields in the response.
		JSONAssert.assertEquals(expectedJsonResponse, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testCreateOrder() throws Exception {
		String orderAsJsonFormat = "{\"order_Id\":1,\"name\":\"Chicken Tikka\",\"price\":\"125\",\"quantity\":1}";

		Mockito.when(orderService.addOrder(Mockito.any(Order.class))).thenReturn(mockOrder);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders").content(orderAsJsonFormat)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMVC.perform(requestBuilder).andReturn();
		System.out.println("Result - " + result.getResponse().getContentAsString());

		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(orderAsJsonFormat, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testGetAllOrders() throws Exception {
		
		List<Order> mockOrderList = new ArrayList<>();
		mockOrderList.add(mockOrder);
		mockOrderList.add(mockOrder);
		
		String orderListasJsonFormat = "["
											+ "{\"order_Id\":1,\"name\":\"Chicken Tikka\",\"price\":\"125\",\"quantity\":1}" + ","
											+ "{\"order_Id\":1,\"name\":\"Chicken Tikka\",\"price\":\"125\",\"quantity\":1}" + "]";
		
		Mockito.when(orderService.getAllOrders()).thenReturn(mockOrderList);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMVC.perform(requestBuilder).andReturn();
		System.out.println("Result - " + result.getResponse().getContentAsString());
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(orderListasJsonFormat, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testDeleteOrder() throws Exception {
		
		Mockito.doNothing().when(orderService).deleteOrder(Mockito.anyLong());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/orders/1");
		MvcResult result = mockMVC.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		
	}

}
