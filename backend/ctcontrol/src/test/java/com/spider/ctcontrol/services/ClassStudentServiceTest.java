package com.spider.ctcontrol.services;

import com.spider.ctcontrol.repositories.ClassStudentRepository;
import com.spider.ctcontrol.repositories.StudentRepository;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;
import com.spider.ctcontrol.services.exceptions.StudentAlreadyEnrolledException;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

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

	@Test
	void addStudentEnrolledException() {
		Long classStudentId = 1L;
		Long studentId = 1L;

		ClassStudent classStudent = new ClassStudent(); 
		classStudent.setId(classStudentId);
		classStudent.setDenomination("Teste ClassStudent");
		
		Student student = new Student();
		student.setId(studentId);
		student.setName("teste 1");

		classStudent.getStudents().add(student);

		when(classStudentRepository.findById(classStudentId)).thenReturn(java.util.Optional.of(classStudent));
		when(studentService.findById(studentId)).thenReturn(student);
		when(studentRepository.save(student)).thenReturn(student);
		when(classStudentRepository.save(classStudent)).thenReturn(classStudent);
		
		assertThrowsExactly(StudentAlreadyEnrolledException.class, () -> {
			classStudentService.addStudent(classStudent.getId(), student.getId());
		});
	}

	@Test
	void removeStudentInSetSuccessfully() {
		Long classStudentId = 1L;
		Long studentId = 1L;

		ClassStudent classStudent = new ClassStudent(); 
		classStudent.setId(classStudentId);
		classStudent.setDenomination("Teste ClassStudent");
		
		Student student = new Student();
		student.setId(studentId);
		student.setName("teste 1");

		classStudent.getStudents().add(student);

		when(classStudentRepository.findById(classStudentId)).thenReturn(java.util.Optional.of(classStudent));
		when(studentService.findById(studentId)).thenReturn(student);
		when(studentRepository.save(student)).thenReturn(student);
		when(classStudentRepository.save(classStudent)).thenReturn(classStudent);
		
		ClassStudent actual = classStudentService.removeStudentInSet(classStudent.getId(), student.getId());

		assertFalse(actual.getStudents().contains(student));
	
	}

	@Test
	void removeStudentInSetResourceNotFoundException() {
		Long classStudentId = 1L;
		Long studentId = 1L;

		when(classStudentRepository.findById(classStudentId)).thenReturn(java.util.Optional.empty());
		
		assertThrowsExactly(ResourceNotFoundException.class, () -> {
			classStudentService.removeStudentInSet(classStudentId, studentId);
		});
	}

	@Test
	void unlinkStudentFromClassStudentSuccessfully() {
		Long classStudentId = 1L;
		Long studentId = 1L;

		ClassStudent classStudent = new ClassStudent(); 
		classStudent.setId(classStudentId);
		classStudent.setDenomination("Teste ClassStudent");
		
		Student student = new Student();
		student.setId(studentId);
		student.setName("teste 1");

		classStudent.getStudents().add(student);
		student.setClassStudent(classStudent);

		when(classStudentRepository.findById(classStudentId)).thenReturn(java.util.Optional.of(classStudent));
		when(studentService.findById(studentId)).thenReturn(student);
		when(studentRepository.save(student)).thenReturn(student);

		when(studentService.unlinkStudent(student)).thenReturn(student);
		when(classStudentRepository.save(classStudent)).thenReturn(classStudent);
		
		Student actual = classStudentService.unlinkStudentAndReturn(student.getId());

		assertNull(actual.getClassStudent());
	
	}

	
}
