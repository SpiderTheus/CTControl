package com.spider.ctcontrol.entities.dtos;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.Student;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MonthlyFeeDto {
    
    private Long id; 
    private String student;
    private String status;
    private Double amount;
    private int dueDay;
    private String lastPayment;

    public MonthlyFeeDto() {
    }

    public MonthlyFeeDto(MonthlyFee monthlyFee){
        
        this.id = monthlyFee.getId();
        this.student = nameStudent(monthlyFee.getStudent());
        this.status = monthlyFee.getStatus().name();
        this.amount = monthlyFee.getAmount();
        this.dueDay = monthlyFee.getDueDay();
        this.lastPayment = String.valueOf(monthlyFee.getLastPayment());

    }

    public String nameStudent(Student student){
        try {
            return student.getName();
        } catch (Exception e) {
            return "Not linked";
        }
    } 
}
