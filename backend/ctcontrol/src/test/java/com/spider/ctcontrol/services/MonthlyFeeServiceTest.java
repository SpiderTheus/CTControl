package com.spider.ctcontrol.services;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.dtos.MonthlyFeeDto;

import com.spider.ctcontrol.entities.enums.PaymentStatus;
import com.spider.ctcontrol.repositories.MonthlyFeeRepository;
import com.spider.ctcontrol.services.exceptions.MonthlyFeeCancelledException;
import com.spider.ctcontrol.services.exceptions.PaymentAlreadyException;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;

import java.util.Optional;

@SpringBootTest
class MonthlyFeeServiceTest {

	@InjectMocks
	private MonthlyFeeService monthlyFeeService;

	@Mock
	private StudentService studentService;

	@Mock
	private MonthlyFeeRepository monthlyFeeRepository;

	@SuppressWarnings("null")
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

	@SuppressWarnings("null")
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

	@SuppressWarnings("null")
	@Test
	void linkMonthlyFee() {

		Student student = new Student();
		student.setId(1L);
		student.setName("Jane Doe");

		MonthlyFee monthlyFee = new MonthlyFee();
		monthlyFee.setAmount(200.0);
		monthlyFee.setDueDay(10);
		monthlyFee.setStatus(PaymentStatus.PENDING);

		when(monthlyFeeRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

		MonthlyFee linkedMonthlyFee = monthlyFeeService.linkMonthlyFee(student, monthlyFee);

		assertNotNull(linkedMonthlyFee);
		assertEquals(student, linkedMonthlyFee.getStudent());
		assertEquals(monthlyFee, student.getMonthlyFee());

		verify(monthlyFeeRepository).save(monthlyFee);
	}

	@SuppressWarnings("null")
	@Test
	void update() {

		Long monthlyFeeId = 1L;

		MonthlyFee existingMonthlyFee = new MonthlyFee();
		existingMonthlyFee.setId(monthlyFeeId);
		existingMonthlyFee.setAmount(100.0);
		existingMonthlyFee.setDueDay(15);
		existingMonthlyFee.setStatus(PaymentStatus.PENDING);

		MonthlyFee updatedDetails = new MonthlyFee();
		updatedDetails.setAmount(150.0);
		updatedDetails.setDueDay(20);
		updatedDetails.setStatus(PaymentStatus.PAID);

		when(monthlyFeeRepository.findById(monthlyFeeId)).thenReturn(java.util.Optional.of(existingMonthlyFee));
		when(monthlyFeeRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

		MonthlyFee updatedMonthlyFee = monthlyFeeService.update(monthlyFeeId, updatedDetails);

		assertNotNull(updatedMonthlyFee);
		assertEquals(150.0, updatedMonthlyFee.getAmount());
		assertEquals(20, updatedMonthlyFee.getDueDay());
		assertEquals(PaymentStatus.PAID, updatedMonthlyFee.getStatus());

		verify(monthlyFeeRepository).findById(monthlyFeeId);
		verify(monthlyFeeRepository).save(existingMonthlyFee);
	}

	@SuppressWarnings("null")
	@Test
	void updateNullKeepAttributes() {

		Long monthlyFeeId = 1L;

		MonthlyFee existingMonthlyFee = new MonthlyFee();
		existingMonthlyFee.setId(monthlyFeeId);
		existingMonthlyFee.setAmount(100.0);
		existingMonthlyFee.setDueDay(15);
		existingMonthlyFee.setStatus(PaymentStatus.PENDING);

		MonthlyFee updatedDetails = new MonthlyFee();
		updatedDetails.setStatus(PaymentStatus.PAID);

		when(monthlyFeeRepository.findById(monthlyFeeId)).thenReturn(Optional.of(existingMonthlyFee));
		when(monthlyFeeRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

		MonthlyFee updatedMonthlyFee = monthlyFeeService.update(monthlyFeeId, updatedDetails);

		assertNotNull(updatedMonthlyFee);
		assertEquals(existingMonthlyFee.getAmount(), updatedMonthlyFee.getAmount());
		assertEquals(existingMonthlyFee.getDueDay(), updatedMonthlyFee.getDueDay());
		assertEquals(PaymentStatus.PAID, updatedMonthlyFee.getStatus());

		verify(monthlyFeeRepository).findById(monthlyFeeId);
		verify(monthlyFeeRepository).save(existingMonthlyFee);
	}

	@SuppressWarnings("null")
	@Test
	void payMonthlyFee() {

		Long monthlyFeeId = 1L;

		MonthlyFee monthlyFee = new MonthlyFee();
		monthlyFee.setId(monthlyFeeId);
		monthlyFee.setAmount(100.0);
		monthlyFee.setDueDay(15);
		monthlyFee.setStatus(PaymentStatus.PENDING);
		monthlyFee.setLastPayment(null);

		when(monthlyFeeRepository.findById(monthlyFeeId)).thenReturn(Optional.of(monthlyFee));
		when(monthlyFeeRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

		MonthlyFee paidMonthlyFee = monthlyFeeService.payMonthlyFee(monthlyFeeId);

		assertNotNull(paidMonthlyFee);
		assertEquals(PaymentStatus.PAID, paidMonthlyFee.getStatus());
		assertEquals(LocalDate.now(), paidMonthlyFee.getLastPayment());

		verify(monthlyFeeRepository).findById(monthlyFeeId);
		verify(monthlyFeeRepository).save(monthlyFee);
	}

	@SuppressWarnings("null")
	@Test
	void paymentAlreadyMonthlyFee() {

		Long monthlyFeeId = 1L;

		MonthlyFee monthlyFee = new MonthlyFee();
		monthlyFee.setId(monthlyFeeId);
		monthlyFee.setAmount(100.0);
		monthlyFee.setDueDay(15);
		monthlyFee.setStatus(PaymentStatus.PAID);
		monthlyFee.setLastPayment(null);

		when(monthlyFeeRepository.findById(monthlyFeeId)).thenReturn(Optional.of(monthlyFee));
		when(monthlyFeeRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

		assertThrows(PaymentAlreadyException.class, () -> monthlyFeeService.payMonthlyFee(monthlyFeeId));

		verify(monthlyFeeRepository).findById(monthlyFeeId);
	}

	@SuppressWarnings("null")
	@Test
	void monthlyFeeCancelled() {

		Long monthlyFeeId = 1L;

		MonthlyFee monthlyFee = new MonthlyFee();
		monthlyFee.setId(monthlyFeeId);
		monthlyFee.setAmount(100.0);
		monthlyFee.setDueDay(15);
		monthlyFee.setStatus(PaymentStatus.CANCELLED);
		monthlyFee.setLastPayment(null);

		when(monthlyFeeRepository.findById(monthlyFeeId)).thenReturn(Optional.of(monthlyFee));
		when(monthlyFeeRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

		assertThrows(MonthlyFeeCancelledException.class, () -> monthlyFeeService.payMonthlyFee(monthlyFeeId));


		verify(monthlyFeeRepository).findById(monthlyFeeId);
	}

	@SuppressWarnings("null")
	@Test
	void changeStatusToPending() {
		LocalDate today = LocalDate.now();
		Long dayToday = Long.valueOf(today.getDayOfMonth());
		Long id = 1L;

		MonthlyFee monthlyFee1 = new MonthlyFee();
		monthlyFee1.setId(id);
		monthlyFee1.setDueDay(dayToday.intValue());
		monthlyFee1.setStatus(PaymentStatus.PAID);

		MonthlyFee monthlyFee2 = new MonthlyFee();
		monthlyFee2.setId(2L);
		monthlyFee2.setDueDay(dayToday.intValue());
		monthlyFee2.setStatus(PaymentStatus.PAID);

		when(monthlyFeeRepository.findByDueDayAndStatus(dayToday, PaymentStatus.PAID)).
		  thenReturn(java.util.Arrays.asList(monthlyFee1, monthlyFee2));

		when(monthlyFeeRepository.saveAll(any())).thenAnswer(i -> i.getArguments()[0]);

		monthlyFeeService.changeStatusToPending();

		assertEquals(PaymentStatus.PENDING, monthlyFee1.getStatus());
		assertEquals(PaymentStatus.PENDING, monthlyFee2.getStatus());

		verify(monthlyFeeRepository).findByDueDayAndStatus(dayToday, PaymentStatus.PAID);
		verify(monthlyFeeRepository).saveAll(any());

	}

	@SuppressWarnings("null")
	@Test
	void NoDebtorsListed() {

		Long dayToday = 5L;
		Long id = 1L;

		MonthlyFee monthlyFee1 = new MonthlyFee();
		monthlyFee1.setId(id);
		monthlyFee1.setDueDay(6);
		monthlyFee1.setStatus(PaymentStatus.PAID);

		MonthlyFee monthlyFee2 = new MonthlyFee();
		monthlyFee2.setId(2L);
		monthlyFee2.setDueDay(7);
		monthlyFee2.setStatus(PaymentStatus.PAID);

		when(monthlyFeeRepository.findByDueDayAndStatus(dayToday, PaymentStatus.PAID)).
		  thenReturn(java.util.Arrays.asList(monthlyFee1, monthlyFee2));

		when(monthlyFeeRepository.saveAll(any())).thenAnswer(i -> i.getArguments()[0]);

		monthlyFeeService.changeStatusToPending();

		assertEquals(PaymentStatus.PAID, monthlyFee1.getStatus());
		assertEquals(PaymentStatus.PAID, monthlyFee2.getStatus());
	}

	@SuppressWarnings("null")
	@Test
	void statusOverdue() {
		LocalDate today = LocalDate.now();
		LocalDate deadline = today.minusDays(3);

		Long id1 = 1L;
		Long id2 = 2L;

		MonthlyFee monthlyFee1 = new MonthlyFee();
		monthlyFee1.setId(id1);
		monthlyFee1.setDueDay(8);
		monthlyFee1.setStatus(PaymentStatus.PENDING);
	

		MonthlyFee monthlyFee2 = new MonthlyFee();
		monthlyFee2.setId(id2);
		monthlyFee2.setDueDay(6);
		monthlyFee2.setStatus(PaymentStatus.PENDING);
		monthlyFee2.setLastPayment(LocalDate.of(2025, 1, 7));

		when(monthlyFeeRepository.findLates(deadline.getDayOfMonth())).
		  thenReturn(java.util.Arrays.asList(monthlyFee1, monthlyFee2));

		when(monthlyFeeRepository.saveAll(any())).thenAnswer(i -> i.getArguments()[0]);

		monthlyFeeService.statusOverdue();

		assertEquals(PaymentStatus.OVERDUE, monthlyFee1.getStatus());
		assertEquals(PaymentStatus.OVERDUE, monthlyFee2.getStatus());

		verify(monthlyFeeRepository).findLates(deadline.getDayOfMonth());
		verify(monthlyFeeRepository).saveAll(any());
	}



}
