package com.spider.ctcontrol.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.enums.PaymentStatus;

public interface MonthlyFeeRepository extends JpaRepository<MonthlyFee, Long> {
   
    List<MonthlyFee> findByDueDayAndStatus(Long dueDay, PaymentStatus status);

    @Query("SELECT f FROM MonthlyFee f WHERE f.dueDay < :deadline AND f.status = 'PENDING'")
    List<MonthlyFee> findLates(@Param("deadline") int deadline);
    
}
