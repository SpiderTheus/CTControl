package com.spider.ctcontrol.services;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.dtos.StudentDto;
import com.spider.ctcontrol.entities.enums.PaymentStatus;
import com.spider.ctcontrol.repositories.StudentRepository;
import com.spider.ctcontrol.services.exceptions.DeleteEntityException;
import com.spider.ctcontrol.services.exceptions.ErrorSearchingException;
import com.spider.ctcontrol.services.exceptions.InsertException;
import com.spider.ctcontrol.services.exceptions.NoResultsFoundException;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student findById(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Student not found with "));
    }

    public List<StudentDto> studenSearchResults (String name) {
        try {

            List<Student> students = repository.findByNameContainingIgnoreCase(name);

            return students.stream()
                    .map(StudentDto::new)
                    .toList();

        } catch (Exception e) {
            throw new ErrorSearchingException("Error searching for students with name: " + name + ". " + e.getMessage());
        }
    }

    public List<Student> searchStudentsName(String name) {
     
             List<Student> students = repository.findByNameContainingIgnoreCase(name);
        
            if (noResultsFound(students)) {
                throw new NoResultsFoundException(name);
            }
       
            return students;
    }

    public boolean noResultsFound(List<Student> students) {
        return !students.isEmpty();
    }

    public List<StudentDto> findAll() {
        return repository.findAll().stream().map(StudentDto::new).toList();
    }

    public Student update(Long id, StudentDto studentDetails) {
        Student student = findById(id);
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());

        return insert(student);
    }


    public void delete(long id) {
        try {
            Student student = findById(id);
            student = unlinkStudent(student);
        
            repository.delete(student);

        } catch (Exception e) {
            throw new DeleteEntityException("Error deleting student with ID: " + id + ". " + e.getMessage());
        } 
    }

    public Student unlinkStudent(Student student) {

        student.getClassStudent().getStudents().remove(student);
        student.getMonthlyFee().setStatus(PaymentStatus.CANCELLED);
        student.setMonthlyFee(null);
        student.setClassStudent(null);
        
        return insert(student);
    }   


    public Student insert(Student student) {
        try {
            Objects.requireNonNull(student, "Student must not be null");
            return repository.save(student);
        } catch (Exception e) {
            throw new InsertException("Error inserting student: " + e.getMessage());
        } 
    }
}