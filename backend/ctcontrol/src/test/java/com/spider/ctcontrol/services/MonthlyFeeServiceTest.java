package com.spider.ctcontrol.services;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.dtos.MonthlyFeeDto;

import com.spider.ctcontrol.entities.enums.PaymentStatus;
import com.spider.ctcontrol.repositories.MonthlyFeeRepository;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MonthlyFeeServiceTest {

	@InjectMocks
	private MonthlyFeeService monthlyFeeService;

	@Mock
	private StudentService studentService;

	@Mock
	private MonthlyFeeRepository monthlyFeeRepository;

	@Test
	void enrollmentCreatingMonthlyFee() {
		Long studentId = 1L;

		Student student = new Student();
		student.setId(studentId);
		student.setName("John Doe");

		when(studentService.insert(student)).thenReturn(student);
		when(studentService.findById(studentId)).thenReturn(student);

		when(monthlyFeeRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

		MonthlyFeeDto monthlyFeeDto = new MonthlyFeeDto();
		monthlyFeeDto.setAmount(100.0);
		monthlyFeeDto.setDueDay(15);
		monthlyFeeDto.setStatus(String.valueOf(PaymentStatus.PENDING));

		MonthlyFee actual = monthlyFeeService.enrollStudent(monthlyFeeDto, studentId);

		assertNotNull(actual);
		assertEquals(student, actual.getStudent());

		verify(studentService).findById(studentId);
	}

	@Test
	void enrollmentUpdateMonthlyFee() {
		Long studentId = 1L;

		MonthlyFee existingMonthlyFee = new MonthlyFee();
		existingMonthlyFee.setId(1L);
		existingMonthlyFee.setAmount(100.0);
		existingMonthlyFee.setDueDay(15);
		existingMonthlyFee.setStatus(PaymentStatus.CANCELLED);

		Student student = new Student();
		student.setId(studentId);
		student.setName("John Doe");
		student.setMonthlyFee(existingMonthlyFee);

		when(studentService.insert(student)).thenReturn(student);
		when(studentService.findById(studentId)).thenReturn(student);

		when(monthlyFeeRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

		MonthlyFeeDto monthlyFeeDto = new MonthlyFeeDto();
		monthlyFeeDto.setAmount(150.0);
		monthlyFeeDto.setDueDay(20);
		monthlyFeeDto.setStatus(String.valueOf(PaymentStatus.PAID));

		MonthlyFee actual = monthlyFeeService.enrollStudent(monthlyFeeDto, studentId);

		assertNotNull(actual);
		assertEquals(student, actual.getStudent());
		assertEquals(150.0, actual.getAmount());
		assertEquals(20, actual.getDueDay());
		assertEquals(PaymentStatus.PAID, actual.getStatus());

		verify(studentService).findById(studentId);
	}

}
