package com.spider.ctcontrol.repositories;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.enums.PaymentStatus;

import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class MonthlyFeeRepositoryTest {
	@Autowired
	private MonthlyFeeRepository monthlyFeeRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findLates() {
		
		MonthlyFee fee1 = new MonthlyFee();
		fee1.setDueDay(2);
		fee1.setStatus(PaymentStatus.PENDING);
		testEntityManager.persist(fee1);

		MonthlyFee fee2 = new MonthlyFee();
		fee2.setDueDay(5);
		fee2.setStatus(PaymentStatus.PENDING);
		testEntityManager.persist(fee2);

		MonthlyFee fee3 = new MonthlyFee();
		fee3.setDueDay(10);
		fee3.setStatus(PaymentStatus.PENDING);
		testEntityManager.persist(fee3);	

		List<MonthlyFee> actual = monthlyFeeRepository.findLates(10);

		assertEquals(2, actual.size());
	}

	@Test
	void findLatesReturnsEmptyWhenNoneFound() {
		MonthlyFee fee1 = new MonthlyFee();
		fee1.setDueDay(15);
		fee1.setStatus(PaymentStatus.PAID);
		testEntityManager.persist(fee1);
		
		List<MonthlyFee> actual = monthlyFeeRepository.findLates(10);
		assertTrue(actual.isEmpty());
	}
}
