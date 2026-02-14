package com.spider.ctcontrol.services;

import com.spider.ctcontrol.repositories.ClassStudentRepository;
import com.spider.ctcontrol.repositories.StudentRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.Student;





@SpringBootTest
class ClassStudentServiceTest {

	@InjectMocks
	private ClassStudentService classStudentService;

	@Mock
	private StudentService studentService;

	@Mock
	private StudentRepository studentRepository;

	@Mock
	private ClassStudentRepository classStudentRepository;


	@Test
	void addStudentSuccessfully() {
		Long classStudentId = 1L;
		Long studentId = 1L;

		ClassStudent classStudent = new ClassStudent(); 
		classStudent.setId(classStudentId);
		classStudent.setDenomination("Teste ClassStudent");
		
		Student student = new Student();
		student.setId(studentId);
		student.setName("teste 1");

		when(classStudentRepository.findById(classStudentId)).thenReturn(java.util.Optional.of(classStudent));
		when(studentService.findById(studentId)).thenReturn(student);
		when(studentRepository.save(student)).thenReturn(student);
		when(classStudentRepository.save(classStudent)).thenReturn(classStudent);
		
		ClassStudent actual = classStudentService.addStudent(classStudent.getId(), student.getId());
		Student actualStudent = actual.getStudents().iterator().next();


		assertEquals(student, actualStudent);
	
	}
}
