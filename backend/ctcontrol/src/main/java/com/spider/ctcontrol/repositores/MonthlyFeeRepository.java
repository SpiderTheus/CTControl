package com.spider.ctcontrol.repositores;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spider.ctcontrol.entities.MonthlyFee;

public interface MonthlyFeeRepository extends JpaRepository<MonthlyFee, Long> {

}
