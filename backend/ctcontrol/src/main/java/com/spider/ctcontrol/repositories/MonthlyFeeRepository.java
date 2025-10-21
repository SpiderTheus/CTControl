package com.spider.ctcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spider.ctcontrol.entities.MonthlyFee;

public interface MonthlyFeeRepository extends JpaRepository<MonthlyFee, Long> {

}
