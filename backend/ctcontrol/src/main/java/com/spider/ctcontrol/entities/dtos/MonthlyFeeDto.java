package com.spider.ctcontrol.entities.dtos;

import com.spider.ctcontrol.entities.MonthlyFee;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MonthlyFeeDto {
    
    private String student;
    private String status;
    private Double amount;
    private int dueDay;


    public MonthlyFeeDto() {
    }

    public MonthlyFeeDto(MonthlyFee monthlyFee){
        this.student = monthlyFee.getStudent().getName();
        this.status = monthlyFee.getStatus().name();
        this.amount = monthlyFee.getAmount();
        this.dueDay = monthlyFee.getDueDay();
    }
}
