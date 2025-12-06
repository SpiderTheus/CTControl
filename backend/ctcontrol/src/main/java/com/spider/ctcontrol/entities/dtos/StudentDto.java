package com.spider.ctcontrol.entities.dtos;


import java.util.Optional;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.enums.Gender;
import com.spider.ctcontrol.entities.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentDto {
    
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private String birthDate;
    private String cpf; 
    private PaymentStatus monthlyFee;
    private String classStudent;

    public StudentDto() {
    }

    public StudentDto(Student student){
        this.id = student.getId();  
        this.name = student.getName();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.gender = student.getGender();
        this.birthDate = student.getBirthDate();
        this.cpf = student.getCpf();
        this.monthlyFee = Optional.ofNullable(student.getMonthlyFee()).map(fee -> fee.getStatus()).orElse(null);
        this.classStudent = Optional.ofNullable(student.getClassStudent()).map(ClassStudent::getDenomination).orElse(null);
    }

}
