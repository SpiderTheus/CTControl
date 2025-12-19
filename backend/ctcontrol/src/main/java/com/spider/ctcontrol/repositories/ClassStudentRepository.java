package com.spider.ctcontrol.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.Student;

public interface ClassStudentRepository extends JpaRepository<ClassStudent, Long> {

}
