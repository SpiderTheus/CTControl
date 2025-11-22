package com.spider.ctcontrol.controllers.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private transient Instant timeInstant;
	private Integer status;
	private String message;
	
	public ReplyMessage() {
		
	}

	public ReplyMessage(Instant timeInstant, Integer status, String message) {
		super();
		this.timeInstant = timeInstant;
		this.status = status;
		this.message = message;
	}

}