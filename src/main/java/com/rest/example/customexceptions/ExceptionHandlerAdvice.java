package com.rest.example.customexceptions;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
	public static final String NOT_OK = "NOTOK";

	//handle custom exception
	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public final ApiError handleEntityNotFoundException(DataNotFoundException ex, WebRequest request,
			HttpServletRequest httpServletReq) {
		return new ApiError(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value(),
				httpServletReq.getMethod(), ex.getMessage(), ex.getCause().toString(), request.getDescription(false));
	}

	// for sending custom validation error messages
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ValidationCustomError customError = new ValidationCustomError("Validation Failed",
				ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(),
				"POST", "Validation Failed", "Validation Failed", request.getDescription(false));
		apiError.getValidationCustomError().add(customError);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

}
