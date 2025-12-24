package com.spider.ctcontrol.entities.dtos;


import java.util.Optional;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.Student;

import com.spider.ctcontrol.entities.enums.PaymentStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudentDto {

    private String name;
    private String email;
    private String phone;
    private PaymentStatus monthlyFee;
    private String classStudent;

    public StudentDto() {
    }

    public StudentDto(Student student){

        this.name = student.getName();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.monthlyFee = Optional.ofNullable(student.getMonthlyFee()).map(fee -> fee.getStatus()).orElse(null);
        this.classStudent = Optional.ofNullable(student.getClassStudent()).map(ClassStudent::getDenomination).orElse(null);
    }

}
