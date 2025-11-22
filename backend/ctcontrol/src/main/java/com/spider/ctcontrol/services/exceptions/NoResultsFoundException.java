package com.spider.ctcontrol.services.exceptions;


public class NoResultsFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NoResultsFoundException(String search) {
		super("No results found for: "+search);
	}

}