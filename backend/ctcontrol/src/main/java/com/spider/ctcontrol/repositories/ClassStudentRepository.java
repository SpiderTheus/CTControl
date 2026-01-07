package com.spider.ctcontrol.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.Student;


public interface ClassStudentRepository extends JpaRepository<ClassStudent, Long> {

      @Query("SELECT f.students FROM ClassStudent f WHERE f.id = :classStudentId")
      Set<Student> findStudentsById(Long classStudentId);
}
