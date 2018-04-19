package com.rest.example.customexceptions;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(include=As.WRAPPER_OBJECT, use=Id.NAME) 
public class ValidationCustomError {

	private LocalDateTime timestamp;
	private String message;
	private String details;

	// constructors
	public ValidationCustomError() {
		this.timestamp = LocalDateTime.now();
	}

	public ValidationCustomError(String message, String details) {
		this();
		this.message = message;
		this.details = details;
	}

	// getters and setters
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
