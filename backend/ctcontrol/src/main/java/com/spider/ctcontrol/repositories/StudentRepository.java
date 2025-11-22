package com.spider.ctcontrol.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.dtos.StudentDto;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<StudentDto> findByNameContainingIgnoreCase(String name);

}
