package com.spider.ctcontrol.entities.dtos;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.Student;

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
        this.student = nameStudent(monthlyFee.getStudent());
        this.status = monthlyFee.getStatus().name();
        this.amount = monthlyFee.getAmount();
        this.dueDay = monthlyFee.getDueDay();
    }

    public String nameStudent(Student student){
        try {
            return student.getName();
        } catch (Exception e) {
            return "Not linked";
        }
    } 
}
