package com.rest.example.customexceptions;

public class CustomDaoException extends CustomBaseException{

	private static final long serialVersionUID = 1L;

	public CustomDaoException() {
		super("CustomDaoException Occured");
	}

	public CustomDaoException(String message) {
		super(message);
	}

	public CustomDaoException(Throwable e) {
		super(e);
	}

	public CustomDaoException(String message, Throwable e) {
		super(message + e);
	}
	
	
}
