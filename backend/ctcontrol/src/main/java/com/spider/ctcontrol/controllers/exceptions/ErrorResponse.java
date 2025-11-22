package com.spider.ctcontrol.controllers.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant timeInstant;
	private Integer status;
	private String error;
	private String message;
	private String path;
	
	public ErrorResponse() {
		
	}

	public ErrorResponse(Instant timeInstant, Integer status, String message, String error, String path) {
		super();
		this.timeInstant = timeInstant;
		this.status = status;
		this.message = message;
		this.error = error;
		this.path = path;
	}
	
}