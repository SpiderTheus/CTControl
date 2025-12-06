package com.spider.ctcontrol.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spider.ctcontrol.entities.MonthlyFee;

import com.spider.ctcontrol.repositories.MonthlyFeeRepository;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;

@Service
public class MonthlyFeeService {
    private final MonthlyFeeRepository repository;

    public MonthlyFeeService(MonthlyFeeRepository repository) {
        this.repository = repository;
    }

    public List<MonthlyFee> findAll() {
        return repository.findAll();
    }

    public MonthlyFee findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Monthly fee, not found with "));
    }

    
    

}
