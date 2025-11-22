package com.spider.ctcontrol.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.dtos.StudentDto;

import com.spider.ctcontrol.repositories.StudentRepository;
import com.spider.ctcontrol.services.exceptions.NoResultsFoundException;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;

@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Student not found with "));
    }

    public StudentDto findByname (String name) {
        return studentRepository.findByNameContainingIgnoreCase(name).orElseThrow(() -> new NoResultsFoundException(name));
    }


    public List<StudentDto> findAll() {
        return studentRepository.findAll().stream().map(student -> new StudentDto(student)).toList();
    }

    public Student insert(Student student) {
        return studentRepository.save(student);
    }


    public Student update(Long id, StudentDto studentDetails) {
        Student student = findById(id);
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());
        student.setGender(studentDetails.getGender());
        student.setBirthDate(studentDetails.getBirthDate());
        student.setCpf(studentDetails.getCpf());

        return studentRepository.save(student);
    }

    public void delete(Long id) {
        Student student = findById(id);
        studentRepository.delete(student);
    }



}
