package com.spider.ctcontrol.services.exceptions;

public class InsertException extends RuntimeException{
      
  private static final long serialVersionUID = 1L;

  public InsertException(String message) {
    super("Insert operation failed: "+message);
  }

}
