package com.spider.ctcontrol.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spider.ctcontrol.services.ClassStudentService;
import com.spider.ctcontrol.entities.ClassStudent;

import com.spider.ctcontrol.entities.dtos.ClassStudentDto;
import com.spider.ctcontrol.entities.dtos.StudentDto;

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

  @GetMapping(value = "/students/{id}")
  public ResponseEntity<Set<StudentDto>> findAllStudents(@PathVariable Long id) {    
      Set<StudentDto> students  = service.findAllStudents(id);
      return ResponseEntity.ok().body(students); 
  }

   @GetMapping(value = "/{id}")
    public ResponseEntity<ClassStudentDto> findById(@PathVariable Long id) {
        ClassStudent classStudent = service.findById(id);

        return ResponseEntity.ok().body(new ClassStudentDto(classStudent));
    } 

   
    @PostMapping(value = "/{teacherId}")
    public ResponseEntity<ClassStudent> insertEntity(@PathVariable Long teacherId,@RequestBody ClassStudent obj) {
        ClassStudent classStudent = service.insert(obj, teacherId);
        return ResponseEntity.ok(classStudent);
    }
}
