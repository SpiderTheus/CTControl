package com.spider.ctcontrol.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.dtos.StudentDto;
import com.spider.ctcontrol.services.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> findAll() {    
        List<StudentDto> students = service.findAll();
        return ResponseEntity.ok().body(students);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StudentDto> findById(@PathVariable Long id) {
        StudentDto student = new StudentDto(service.findById(id));
        return ResponseEntity.ok().body(student);
    } 

    @GetMapping(value = "/search/{name}")
    public ResponseEntity<List<StudentDto>> findByName(@PathVariable String name) {
        List<StudentDto> students = service.findByName(name);
        return ResponseEntity.ok().body(students);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student obj) {
        Student student = service.insert(obj);
        return ResponseEntity.ok(student);
    }


}