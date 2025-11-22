package com.spider.ctcontrol.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Object id, String about) {
		super(about + "ID "+id);
	}
	
}