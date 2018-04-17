package com.rest.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  //for resolving error when trying to display data in json format
@Entity
@Table(name = "ORDERS")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ID")
	private long order_Id;
	
	private String name;
	private String price;
	private int quantity;
	
	// constructors
	public Order() {}
	
	public Order(long order_Id, String name, String price, int quantity) {
		super();
		this.order_Id = order_Id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public Order(String name, String price, int quantity) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	// getters and setters

	public long getOrder_Id() {
		return order_Id;
	}

	public void setOrder_Id(long order_Id) {
		this.order_Id = order_Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
