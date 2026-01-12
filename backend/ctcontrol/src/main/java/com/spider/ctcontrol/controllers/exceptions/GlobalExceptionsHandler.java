package com.spider.ctcontrol.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spider.ctcontrol.services.exceptions.DeleteEntityException;
import com.spider.ctcontrol.services.exceptions.EnrollingException;
import com.spider.ctcontrol.services.exceptions.ErrorSearchingException;
import com.spider.ctcontrol.services.exceptions.InsertException;
import com.spider.ctcontrol.services.exceptions.NoResultsFoundException;
import com.spider.ctcontrol.services.exceptions.PaymentAlreadyException;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;
import com.spider.ctcontrol.services.exceptions.StudentAlreadyEnrolledException;

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

	@ExceptionHandler(PaymentAlreadyException.class)
	public ResponseEntity<ReplyMessage> paymentAlreadyException(Throwable e) {
		var mensage = e.getMessage();
		HttpStatus status = HttpStatus.CONFLICT;

		var err = new ReplyMessage(Instant.now(), status.value(), mensage);

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(StudentAlreadyEnrolledException.class)
	public ResponseEntity<ReplyMessage> studentAlreadyEnrolledException(StudentAlreadyEnrolledException e, HttpServletRequest request) {
		var mensage = "Student already enrolled";
		HttpStatus status = HttpStatus.CONFLICT;

		var err = new ReplyMessage(Instant.now(), status.value(), mensage);

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(InsertException.class)
	public ResponseEntity<ReplyMessage> insertException(InsertException e, HttpServletRequest request) {
		var mensage = "Error inserting data";
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

		var err = new ReplyMessage(Instant.now(), status.value(), mensage);
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(EnrollingException.class)
	public ResponseEntity<ReplyMessage> enrollingException(EnrollingException e, HttpServletRequest request) {
		var mensage = "Error enrolling student";
		HttpStatus status = HttpStatus.BAD_REQUEST;

		var err = new ReplyMessage(Instant.now(), status.value(), mensage);
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DeleteEntityException.class)
	public ResponseEntity<ReplyMessage> deleteEntityException(DeleteEntityException e, HttpServletRequest request) {
		var mensage = "Error deleting entity";
		HttpStatus status = HttpStatus.BAD_REQUEST;

		var err = new ReplyMessage(Instant.now(), status.value(), mensage);
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(ErrorSearchingException.class)
	public ResponseEntity<ReplyMessage> errorSearchingException(ErrorSearchingException e, HttpServletRequest request) {
		var mensage = "Error during search operation";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		var err = new ReplyMessage(Instant.now(), status.value(), mensage);
		return ResponseEntity.status(status).body(err);
	}
}