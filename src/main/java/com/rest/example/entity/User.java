package com.rest.example.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  //for resolving error when trying to display data in json format
@Entity
@Table(name = "USER")
@XmlRootElement
public class User extends ResourceSupport { //extended with ResourceSupport class for HATEOS functionality

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_id_seq_gen")
	@SequenceGenerator(name = "user_id_seq_gen", sequenceName = "user_id_seq", allocationSize = 1)
	@Column(name = "USER_ID", unique = true, nullable = false, length = 20)
	private long userId;

	@Column(name = "USERNAME", length = 15, nullable = false, unique = true)
	private String username;

	@Column(name = "PASSWORD", length = 15, nullable = false)
	private String password;

	@Column(name = "CREATIONDATE", nullable = false)
	private Timestamp creationDate;

	@Column(name = "ISACTIVE")
	private boolean isActive;

	@Transient
	private String userTypeValue;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USERTYPE_ID", nullable = false)
	private UserType userType;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ORDERS", joinColumns = { @JoinColumn(name = "USER_ID")},inverseJoinColumns = { @JoinColumn(name = "ORDER_ID")})
	private Set<Order> orders = new HashSet<>();

	// setters and getters
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getUserTypeValue() {
		return userTypeValue;
	}

	public void setUserTypeValue(String userTypeValue) {
		this.userTypeValue = userTypeValue.toUpperCase();
	}

	public void setUser(User  user)
	{
		this.username=user.getUsername();
		this.password=user.getPassword();
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	
}
