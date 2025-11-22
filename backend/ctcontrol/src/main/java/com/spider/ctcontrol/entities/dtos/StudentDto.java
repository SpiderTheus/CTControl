package com.spider.ctcontrol.entities.dtos;


import java.util.Optional;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.enums.Gender;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentDto {
    
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private String birthDate;
    private String cpf; 
    private double monthlyFee;
    private String classStudent;

    public StudentDto() {
    }

    public StudentDto(Student student){
        this.name = student.getName();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.gender = student.getGender();
        this.birthDate = student.getBirthDate();
        this.cpf = student.getCpf();
        this.monthlyFee = Optional.ofNullable(student.getMonthlyFee())
                          .map(MonthlyFee::getAmount)
                          .orElse(0.0);
        this.classStudent = Optional.ofNullable(student.getClassStudent()).map(ClassStudent::getDenomination).orElse(null);
    }

}
