package com.rest.example.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rest.example.customexceptions.CustomBaseException;
import com.rest.example.customexceptions.CustomDaoException;
import com.rest.example.entity.Order;
import com.rest.example.entity.User;
import com.rest.example.entity.UserType;
import com.rest.example.service.OrderService;
import com.rest.example.service.UserService;
import com.rest.example.service.UserTypeService;

@RestController
@RequestMapping("/users")
public class UserRestController {

	Logger logger = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	UserService userService;

	@Autowired
	UserTypeService userTypeService;

	@Autowired
	OrderService orderService;

	/**
	 * Add a new user
	 */
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public User createUser(@RequestBody User user) throws CustomBaseException {
		return userService.createUser(user);
	}

	/**
	 * Get a specific user based on its id(primary key)
	 * 
	 * Content negotiation is implmeneted here based on Accept header sent by the
	 * client Accept: application/json - will return json response or default is
	 * json response if no Accept header given Accept: application/xml - will return
	 * xml response
	 * 
	 * HATEOS is implemented here using spring boot hateos library
	 */
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody User getUser(@PathVariable long id) throws CustomBaseException {
		User user = userService.getUser(id);
		// HATEOS functionality - adding links to response
		return addLinks(user);

	}

	/**
	 * Get all users
	 */
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<User> getAllUsers() throws CustomBaseException {
		return userService.getAllUsers();
	}

	/**
	 * delete a user based on id
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody String deleteUser(@PathVariable long id) throws CustomBaseException {
		return userService.deleteUser(id);
	}

	/**
	 * Update username
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody User updateUser(@PathVariable long id, @RequestBody User user) throws CustomBaseException {
		return userService.updateUserName(id, user.getUsername());
	}

	/**
	 * Add a Order for a User
	 * 
	 * @throws CustomBaseException
	 */
	@PostMapping(value = "/{userid}/orders")
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody User addUserOrder(@PathVariable long userid, @RequestBody Order order)
			throws CustomBaseException {

		User user = getUser(userid);
		user.getOrders().add(order);
		return userService.updateUser(user);
	}

	/**
	 * Get one Order for the user
	 * 
	 * @throws CustomDaoException
	 */
	@GetMapping(value = "/{userid}/orders/{orderid}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Order getOneOrder(@PathVariable long userid, @PathVariable long orderid)
			throws CustomDaoException {

		User user = userService.getUser(userid);
		return user.getOrders().stream().filter(order -> order.getOrder_Id() == orderid).findAny().orElse(null);
	}

	/**
	 * Get All Orders for the user
	 */
	@GetMapping(value = "/{userid}/orders")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Order> getAllOrdersForTheUser(@PathVariable long userid) throws CustomDaoException {

		User user = userService.getUser(userid);
		return user.getOrders().stream().collect(Collectors.toList());
	}

	/**
	 * Filter based on user type
	 * 
	 * /users?user-type=Admin
	 * 
	 */
	@GetMapping(params = { "user-type" })
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<User> getUsersOfUserType(@RequestParam(value = "user-type") String userType)
			throws CustomBaseException {
		UserType usertype = userTypeService.geTypeOfUsers(userType);
		return userService.getAllUsersBasedOnUserType(usertype);
	}

	/**
	 * Add links for HATEOS functionality
	 * 
	 * @param User
	 * @return User
	 * @throws CustomDaoException
	 */
	private User addLinks(User user) throws CustomDaoException {

		// adding a self link
		Link selfLink = ControllerLinkBuilder.linkTo(UserRestController.class).slash(user.getUserId()).withSelfRel();
		user.add(selfLink);

		// adding a link to all the orders for the user
		List<Order> methodLinkBuilder = ControllerLinkBuilder.methodOn(UserRestController.class)
				.getAllOrdersForTheUser(user.getUserId());
		Link ordersLink = ControllerLinkBuilder.linkTo(methodLinkBuilder).withRel("allOrders");
		user.add(ordersLink);

		return user;
	}

}
