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
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Student not found with "));
    }

    public List<StudentDto> findByName (String name) {
    	List<StudentDto> students = repository.findByNameContainingIgnoreCase(name).stream().map(StudentDto::new).toList();
		if (students.isEmpty() || students == null)
			throw new NoResultsFoundException(name);

		return students;
    }

    public List<StudentDto> findAll() {
        return repository.findAll().stream().map(student -> new StudentDto(student)).toList();
    }

    public Student insert(Student student) {
        try {
            return repository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Error inserting student: " + e.getMessage());
        } 
    }


    public Student update(Long id, StudentDto studentDetails) {
        Student student = findById(id);
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());
        student.setGender(studentDetails.getGender());
        student.setBirthDate(studentDetails.getBirthDate());
        student.setCpf(studentDetails.getCpf());

        return repository.save(student);
    }

    public void delete(Long id) {
        Student student = findById(id);
        repository.delete(student);
    }



}
