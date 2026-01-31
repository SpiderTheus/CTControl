package com.spider.ctcontrol.services.exceptions;

public class MonthlyFeeCancelledException extends RuntimeException{

  private static final long serialVersionUID = 1L;

  public MonthlyFeeCancelledException(Long id, String message) {
    super("Monthly fee with ID " + id + " is cancelled. " + message);
  }

}
