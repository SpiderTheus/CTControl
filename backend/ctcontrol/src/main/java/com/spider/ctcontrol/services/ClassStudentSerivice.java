package com.spider.ctcontrol.services;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.repositories.ClassStudentRepository;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;
import com.spider.ctcontrol.services.exceptions.StudentAlreadyEnrolledException;

@Service
public class ClassStudentSerivice {
    
  private final ClassStudentRepository repository;

  public ClassStudentSerivice(ClassStudentRepository repository) {
      this.repository = repository;
  }

  public List<ClassStudent> findAll() {
      return repository.findAll();
  }

  public Set<Student> findAllStudents(Long classStudentId) {
    
      ClassStudent classStudent = findById(classStudentId);

      return classStudent.getStudents();
  }

  public ClassStudent findById(long id) {
      return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "ClassStudent, not found with "));
  } 
  
  public ClassStudent insert(ClassStudent ClassStudent) {
        try {
            Objects.requireNonNull(ClassStudent, "ClassStudent must not be null");
            return repository.save(ClassStudent);
        } catch (Exception e) {
            throw new RuntimeException("Error inserting ClassStudent: " + e.getMessage());
        } 
    }

  public ClassStudent insertStudent(Long classStudentId, Student student) {
      ClassStudent classStudent = findById(classStudentId);
        if (classStudent.getStudents().contains(student)) {
            throw new StudentAlreadyEnrolledException(student.getId(), classStudentId);
          } else{
            classStudent.getStudents().add(student);
            return repository.save(classStudent);
          }
    }
  
  public ClassStudent update(Long id, ClassStudent classStudentDetails) {
      ClassStudent classStudent = findById(id);

      classStudent.setTime(classStudentDetails.getTime());
      classStudent.setCostMonthly(classStudentDetails.getCostMonthly());
      classStudent.setDaysWeek(classStudentDetails.getDaysWeek());
      classStudent.setModality(classStudentDetails.getModality());
      classStudent.setDenomination(classStudentDetails.getModality() + " - " + classStudentDetails.getTeacher().getName());

      return repository.save(classStudent);
  }

  public ClassStudent deleteStudent(Long classStudentId, Long studentId) {
      ClassStudent classStudent = findById(classStudentId);
      classStudent.getStudents().removeIf(student -> Objects.equals(student.getId(), studentId));
      return repository.save(classStudent);
  }

  public void delete(long id) {
      ClassStudent classStudent = findById(id);
      Objects.requireNonNull(classStudent, "ClassStudent must not be null");
      repository.delete(classStudent);
  }

}   



