package com.spider.ctcontrol.services;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.dtos.StudentDto;
import com.spider.ctcontrol.repositories.StudentRepository;
import com.spider.ctcontrol.services.exceptions.NoResultsFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;
import com.spider.ctcontrol.entities.enums.Gender;
import com.spider.ctcontrol.entities.enums.PaymentStatus;


@SpringBootTest
class StudentServiceTest {

	@InjectMocks
	private StudentService studentService;

	@Mock
	private StudentRepository studentRepository;

	@Test
	void studentFindsResult() {
		String search = "abc";

		Student student1 = new Student();
		student1.setName("abc");


		Student student2 = new Student();
		student2.setName("xyzabc");

		List<Student> falseStudents = new ArrayList<>();
		falseStudents.add(student1);
		falseStudents.add(student2);

		when(studentRepository.findByNameContainingIgnoreCase(search)).thenReturn(falseStudents);

		List<StudentDto> actual = studentService.studentSearchResults(search);

		assertEquals(2, actual.size());

		verify(studentRepository, times(1)).findByNameContainingIgnoreCase(search);
	}

	@Test
	void studentFindsNoResult() {
		String search = "noresult";

		List<Student> falseStudents = new ArrayList<>();

		when(studentRepository.findByNameContainingIgnoreCase(search)).thenReturn(falseStudents);

		assertThrows(NoResultsFoundException.class, () -> studentService.searchStudentsName(search));

		verify(studentRepository, times(1)).findByNameContainingIgnoreCase(search);
	}

	@Test
	void UpdatedStudent() {
		Long studentId = 1L;

		Student existingStudent = new Student();

		existingStudent.setId(studentId);
		existingStudent.setName("Old Name");
		existingStudent.setEmail("old@example.com");
		existingStudent.setPhone("123456789");

		StudentDto studentDetails = new StudentDto(new Student("New Name", "new@example.com", "987654321", null, null, null));

		when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
		when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

		Student actual = studentService.update(studentId, studentDetails);

		assertEquals(existingStudent, actual);
	}

	@Test
	void unlinkStudent() {
		Student student = new Student("abc", "abc", "abc", Gender.FEMALE, "abc", "abc");
		MonthlyFee monthlyFee = new MonthlyFee();
		monthlyFee.setStatus(PaymentStatus.PAID);
		monthlyFee.setStudent(student);
		student.setMonthlyFee(monthlyFee);

		ClassStudent classStudent = new ClassStudent();
		classStudent.setDenomination("teste");
		classStudent.getStudents().add(student);
		student.setClassStudent(classStudent);
		
		when(studentRepository.save(student)).thenReturn(student);

		Student actual = studentService.unlinkStudent(student);

		assertNull(actual.getClassStudent());
		assertNull(actual.getMonthlyFee());

		verify(studentRepository, times(1)).save(student);
	}



}
