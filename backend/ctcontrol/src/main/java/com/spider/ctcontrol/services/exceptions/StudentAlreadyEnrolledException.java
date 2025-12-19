package com.spider.ctcontrol.services.exceptions;

public class StudentAlreadyEnrolledException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  public StudentAlreadyEnrolledException(Object studentId, Object classStudentId) {
    super("Student ID "+studentId+" is already enrolled in ClassStudent ID "+classStudentId);
  } 

}
