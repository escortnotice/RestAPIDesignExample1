package com.rest.example.customexceptions;

public class CustomBaseException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String exceptionMessage;

	public CustomBaseException() {
		this.exceptionMessage = "BaseException Occurred";
	}

	public CustomBaseException(String message) {
		super(message);
		this.exceptionMessage = message;
	}

	public CustomBaseException(Throwable e) {
		super(e);
		this.exceptionMessage = e.getMessage();
	}

	public CustomBaseException(String message, Throwable e) {
		super(message + e);
		this.exceptionMessage = message;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + ": " + exceptionMessage;
	}

	@Override
	public String getMessage() {
		return exceptionMessage;
	}
}
