package com.spider.ctcontrol.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.spider.ctcontrol.entities.dtos.StudentDto;
import com.spider.ctcontrol.services.StudentService;
import org.springframework.web.bind.annotation.GetMapping;



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



}