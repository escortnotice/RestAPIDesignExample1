package com.rest.example.customexceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(include=As.WRAPPER_OBJECT, use=Id.NAME)    //to add the class name in the json output response
public class ApiError {
	
	private String httpStatus;
	private int httpStatusCode;
	private String httpMethod;
	private LocalDateTime timestamp;
	private String userMessage;
	private String systemError;
	private String path;
	private List<ValidationCustomError> validationCustomError = new ArrayList<>();
	
	public ApiError() {
		this.timestamp = LocalDateTime.now();
	}

	public ApiError(String httpStatus, int httpStatusCode, String httpMethod, String userMessage, String systemError,
			String path) {
		this();
		this.httpStatus = httpStatus;
		this.httpStatusCode = httpStatusCode;
		this.httpMethod = httpMethod;
		this.userMessage = userMessage;
		this.systemError = systemError;
		this.path = path;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getSystemError() {
		return systemError;
	}

	public void setSystemError(String systemError) {
		this.systemError = systemError;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<ValidationCustomError> getValidationCustomError() {
		return validationCustomError;
	}

	public void setValidationCustomError(List<ValidationCustomError> validationCustomError) {
		this.validationCustomError = validationCustomError;
	}
	
	
	
}
