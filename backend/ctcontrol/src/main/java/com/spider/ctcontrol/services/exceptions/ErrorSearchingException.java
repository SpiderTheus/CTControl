package com.spider.ctcontrol.services.exceptions;

public class ErrorSearchingException extends RuntimeException{
      
  private static final long serialVersionUID = 1L;

  public ErrorSearchingException(String message) {
    super("Error occurred during search operation: "+message);
  }

}
