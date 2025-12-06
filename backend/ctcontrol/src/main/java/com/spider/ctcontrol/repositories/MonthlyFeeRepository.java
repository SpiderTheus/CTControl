package com.spider.ctcontrol.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.enums.PaymentStatus;

public interface MonthlyFeeRepository extends JpaRepository<MonthlyFee, Long> {
   
    List<MonthlyFee> findByStatus(PaymentStatus status);
}
