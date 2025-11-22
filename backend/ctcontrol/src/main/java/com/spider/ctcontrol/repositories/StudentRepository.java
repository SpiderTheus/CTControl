package com.spider.ctcontrol.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spider.ctcontrol.entities.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameContainingIgnoreCase(String name);

}
