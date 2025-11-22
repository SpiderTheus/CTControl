package com.spider.ctcontrol.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spider.ctcontrol.services.exceptions.NoResultsFoundException;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionsHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundException(Throwable e, HttpServletRequest request) {
		var mensage = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		var err = new ErrorResponse(Instant.now(), status.value(), mensage, e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(NoResultsFoundException.class)
	public ResponseEntity<ReplyMessage> noResultsFoundException(Throwable e) {
		var mensage = e.getMessage();
		HttpStatus status = HttpStatus.NOT_FOUND;

		var err = new ReplyMessage(Instant.now(), status.value(), mensage);

		return ResponseEntity.status(status).body(err);
	}

}
