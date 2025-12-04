package com.spider.ctcontrol.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.dtos.StudentDto;
import com.spider.ctcontrol.services.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



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
    public ResponseEntity<Student> insertEntity(@RequestBody Student obj) {
        Student student = service.insert(obj);
        return ResponseEntity.ok(student);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto obj) {
        Student updatedStudent = service.update(id, obj);
        StudentDto studentDto = new StudentDto(updatedStudent);
        return ResponseEntity.ok(studentDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); 
    }
}