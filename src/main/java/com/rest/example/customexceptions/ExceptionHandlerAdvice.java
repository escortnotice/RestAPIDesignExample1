package com.rest.example.customexceptions;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class ExceptionHandlerAdvice {

	Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
	public static final String NOT_OK = "NOTOK";


	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public final ApiError handleEntityNotFoundException(DataNotFoundException ex, WebRequest request,HttpServletRequest httpServletReq) {
		return new ApiError(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value(), httpServletReq.getMethod(),
				ex.getMessage(), ex.getCause().toString(), request.getDescription(false));
	}

}
