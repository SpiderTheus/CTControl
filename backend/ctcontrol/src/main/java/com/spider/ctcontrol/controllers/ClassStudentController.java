package com.spider.ctcontrol.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spider.ctcontrol.services.ClassStudentService;
import com.spider.ctcontrol.entities.dtos.ClassStudentDto;

@RestController
@RequestMapping(value = "/class-students")
public class ClassStudentController {

  private final ClassStudentService service;

 
  public ClassStudentController(ClassStudentService service) {
      this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<ClassStudentDto>> findAll() {    
      List<ClassStudentDto> classStudents  = service.findAll();
      return ResponseEntity.ok().body(classStudents); 
  }

}
