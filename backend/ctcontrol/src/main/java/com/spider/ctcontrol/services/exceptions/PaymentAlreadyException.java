package com.spider.ctcontrol.services.exceptions;

public class PaymentAlreadyException  extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PaymentAlreadyException(String message) {
		super(message);
	}
}
