package com.rest.example.service;


import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.example.customexceptions.DataNotFoundException;
import com.rest.example.entity.Order;
import com.rest.example.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepo;
	
	@Transactional
	public Order addOrder(Order order) {
		return orderRepo.save(order);
	}
	
	@Transactional(readOnly = true )
	public Order getOrder(long id) throws DataNotFoundException {
		try {
			Order order = orderRepo.getOne(id);
			if(order.getName()!=null)
				return order;
			return order;
		}
		catch(EntityNotFoundException ex) {
			throw new DataNotFoundException(Order.class,"id",Long.toString(id),ex);
		}	

	}

	@Transactional(readOnly = true )
	public List<Order> getAllOrders() {
		return orderRepo.findAll();
	}
	
	@Transactional
	public void deleteOrder(long id) {
		orderRepo.deleteById(id);
	}

}
