package com.spider.ctcontrol.services;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.dtos.ClassStudentDto;
import com.spider.ctcontrol.entities.dtos.StudentDto;
import com.spider.ctcontrol.repositories.ClassStudentRepository;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;
import com.spider.ctcontrol.services.exceptions.StudentAlreadyEnrolledException;

@Service
public class ClassStudentService {
    
  private final ClassStudentRepository repository;

  private final TeacherService teacherService;  

  private final StudentService studentService;

  public ClassStudentService(ClassStudentRepository repository, TeacherService teacherService, StudentService studentService) {
      this.repository = repository;
      this.teacherService = teacherService;
      this.studentService = studentService;
  }

  public List<ClassStudentDto> findAll() {
    
      return repository.findAll().stream()
              .map(ClassStudentDto::new)
              .toList();
  }

  public Set<StudentDto> findAllStudents(Long classStudentId) {
    
    ClassStudent classStudent = findById(classStudentId);
    Set<StudentDto> students = new HashSet<>();
        
      for (Student student : classStudent.getStudents()) {
          students.add(new StudentDto(student));
      }

      return students;
  }

  public ClassStudent findById(long id) {
      return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "ClassStudent, not found with "));
  } 
  
  public ClassStudent insert(ClassStudent ClassStudent, Long teacherId) {
        try {
            Objects.requireNonNull(ClassStudent, "ClassStudent must not be null");
            ClassStudent.setTeacher(teacherService.findById(teacherId));
            return repository.save(ClassStudent);
        } catch (Exception e) {
            throw new RuntimeException("Error inserting ClassStudent: " + e.getMessage());
        } 
    }

  public ClassStudent addStudent(Long classStudentId, Long studentId) {
      ClassStudent classStudent = findById(classStudentId);
      Student student = studentService.findById(studentId);


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



