package com.rest.example.customexceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ExceptionHandlerAdvice {

	Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
	public static final String NOT_OK = "NOTOK";

	@ExceptionHandler(CustomBaseException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handleException(CustomBaseException e) {
		logger.error("Caught CustomBaseException in ExceptionHandlerAdvice class:" , e);
		return NOT_OK;
	}
}
