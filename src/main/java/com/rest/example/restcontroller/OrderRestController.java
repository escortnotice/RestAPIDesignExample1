package com.rest.example.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rest.example.customexceptions.DataNotFoundException;
import com.rest.example.entity.Order;
import com.rest.example.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public Order createOrder(@Valid @RequestBody Order order) {
		return orderService.addOrder(order);
	}

	@GetMapping(value = "/{id}", produces = { "application/json"})
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Order getOrder(@PathVariable long id) throws DataNotFoundException {
		return orderService.getOrder(id);

	}
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Order> getAllOrders() {
		return orderService.getAllOrders();

	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody void deleteOrder(@PathVariable long id) {
		orderService.deleteOrder(id);

	}
}
