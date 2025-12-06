package com.spider.ctcontrol.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.dtos.MonthlyFeeDto;
import com.spider.ctcontrol.services.MonthlyFeeService;

@RestController
@RequestMapping(value = "/monthlyfees")
public class MonthlyFeeController {

    private final MonthlyFeeService service;

    public MonthlyFeeController(MonthlyFeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MonthlyFeeDto>> findAll() {    
        List<MonthlyFeeDto> MonthlyFees = service.findAll().stream().map(MonthlyFeeDto::new).toList();
        return ResponseEntity.ok().body(MonthlyFees);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MonthlyFee> findById(@PathVariable Long id) {
        MonthlyFee monthlyFee = service.findById(id);

        return ResponseEntity.ok().body(monthlyFee);
    } 

}
