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
import com.spider.ctcontrol.entities.dtos.ClassStudentDetails;
import com.spider.ctcontrol.repositories.ClassStudentRepository;
import com.spider.ctcontrol.services.exceptions.DeleteEntityException;

import com.spider.ctcontrol.services.exceptions.InsertException;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;
import com.spider.ctcontrol.services.exceptions.StudentAlreadyEnrolledException;

import jakarta.transaction.Transactional;

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

    public Set<StudentDto> findAllStudentsDto(Long classStudentId) {

        Set<Student> students = findAllStudents(classStudentId);
        Set<StudentDto> studentDtos = new HashSet<>();

        for (Student student : students) {
            studentDtos.add(new StudentDto(student));
        }

        return studentDtos;
  }

    public Set<Student> findAllStudents(Long classStudentId) {
        findById(classStudentId);
        return repository.findStudentsById(classStudentId);
  }

    public ClassStudent findById(long id) {
      return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "ClassStudent, not found with "));
  } 

    public ClassStudent insert(ClassStudent classStudent, Long teacherId) {
        try {
            classStudent.setTeacher(teacherService.findById(teacherId));

            return insert(classStudent);
        } catch (Exception e) {
            throw new InsertException("ClassStudent");
        } 
    }

    @Transactional
    public ClassStudentDto addStudentInClassStudent(Long classStudentId, Long studentId) {
       try {
        return new ClassStudentDto(addStudent(classStudentId, studentId));}
        catch (Exception e) {
            throw new InsertException("Error adding student with ID: " + studentId + " to classStudent with ID: " + classStudentId + ". " + e.getMessage());
        }
    }

    @Transactional
    public ClassStudent addStudent(Long classStudentId, Long studentId) {
  
        ClassStudent classStudent = findById(classStudentId);
        Student student = studentService.findById(studentId);
        
        if (classStudent.getStudents().contains(student)) {
            throw new StudentAlreadyEnrolledException(student.getId(), classStudentId);
          } else{
            student.setClassStudent(classStudent);
            classStudent.getStudents().add(student);

            return insert(classStudent);
          }
    }

    public ClassStudent update(Long id, ClassStudentDetails classStudentDetails) {
      ClassStudent classStudent = findById(id);

      classStudent.setTime(classStudentDetails.getTime());
      classStudent.setCostMonthly(classStudentDetails.getCostMonthly());
      classStudent.setDaysWeek(classStudentDetails.getDaysWeek());
      classStudent.setModality(classStudentDetails.getModality());
      classStudent.setDenomination(classStudentDetails.getModality() + " - " + classStudentDetails.getDaysWeek() + " - " + classStudentDetails.getTime());

      return insert(classStudent);
    } 

    @Transactional
    public ClassStudentDto removeStudent(Long classStudentId, Long studentId) {
        try {
            return new ClassStudentDto(removeStudentInSet(classStudentId, studentId));
        } catch (Exception e) {
            throw new DeleteEntityException("Error removing student with ID: " + studentId + " from classStudent with ID: " + classStudentId + ". " + e.getMessage());
        }
    }
  
    @Transactional
    public ClassStudent removeStudentInSet(Long classStudentId, Long studentId) {
        ClassStudent classStudent = findById(classStudentId);
        Student student = unlinkStudentAndReturn(studentId);
        
        classStudent.getStudents().remove(student);
       
        return insert(classStudent);
    }

    @Transactional
    public Student unlinkStudentAndReturn(long studentId) {
        Student student = studentService.findById(studentId);
       
        student.setClassStudent(null);

        return student;
    }

    public ClassStudent insert(ClassStudent classStudent){
        Objects.requireNonNull(classStudent, "ClassStudent must not be null");
        return repository.save(classStudent);
    }

    public void delete(Long classStudentId){ 
        try {
            ClassStudent classStudent = unlinkStudentAndTeacher(classStudentId);
            Objects.requireNonNull(classStudent, "ClassStudent must not be null");

            repository.delete(classStudent);
        } catch (Exception e) {
         throw new DeleteEntityException("Error deleting classStudent");
     }
    }

  public ClassStudent unlinkStudentAndTeacher(Long classStudentId){
     ClassStudent classStudent = findById(classStudentId);

      for (Student student : classStudent.getStudents()) {
          student.setClassStudent(null);
      }
      
      classStudent.setTeacher(null);
      classStudent.setStudents(null);

      return classStudent;
  }
}



