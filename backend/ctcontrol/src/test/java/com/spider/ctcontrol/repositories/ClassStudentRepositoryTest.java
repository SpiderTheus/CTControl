package com.spider.ctcontrol.repositories;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.Student;
import java.util.Set;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class ClassStudentRepositoryTest {
	
	@Autowired
	private ClassStudentRepository classStudentRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findStudentsById() {

		Student student1 = new Student();
		student1.setName("John Doe");
		testEntityManager.persist(student1);

		Student student2 = new Student();
		student2.setName("Jane Smith");
		testEntityManager.persist(student2);

		ClassStudent classStudent = new ClassStudent();
		
		classStudent.addStudent(student1);
		classStudent.addStudent(student2);
		
		testEntityManager.persist(classStudent);
		testEntityManager.flush();

		Set<Student> actualStudents = classStudentRepository.findStudentsById(classStudent.getId());

		
		assertNotNull(actualStudents);
		assertEquals(2, actualStudents.size());
		assertTrue(actualStudents.contains(student1));
		assertTrue(actualStudents.contains(student2));
	}

	@Test
	void findStudentsByIdReturnsEmptyWhenNotFound() {
		Long classStudentIdNotFound = 9999L;

		Set<Student> actual = classStudentRepository.findStudentsById(classStudentIdNotFound);

		assertTrue(actual.isEmpty());
	}
}

