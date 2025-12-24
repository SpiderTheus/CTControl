package com.spider.ctcontrol.services;

import org.springframework.stereotype.Service;

import com.spider.ctcontrol.entities.Teacher;
import com.spider.ctcontrol.repositories.TeacherRepository;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;

@Service
public class TeacherService {
    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public Teacher findById(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Teacher not found with "));
    }
  }
