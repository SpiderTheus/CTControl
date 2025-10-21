package com.spider.ctcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spider.ctcontrol.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
