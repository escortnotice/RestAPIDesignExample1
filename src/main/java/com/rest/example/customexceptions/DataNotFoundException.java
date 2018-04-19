package com.rest.example.customexceptions;

public class DataNotFoundException extends Throwable {

	private static final long serialVersionUID = 1L;

	/**
	 * passes "custom message" and "cause" as the arguments to the super
	 * class(Throwable).
	 */
	public DataNotFoundException(@SuppressWarnings("rawtypes") Class class1, String propertyName, String propertyValue,
			Throwable cause) {
		super(generateErrorMessage(class1, propertyName, propertyValue), cause);
	}

	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataNotFoundException(String message) {
		super(message);
	}

	//generate the custom message
	private static String generateErrorMessage(@SuppressWarnings("rawtypes") Class class1, String propertyName,
			String propertyValue) {

		return class1.getSimpleName().toUpperCase() + " was not found for " + propertyName + " - " + propertyValue;

	}

}
