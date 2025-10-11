package com.spider.ctcontrol.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_students")
public class Student {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private String birthDate;
    private String cpf;
    
    @ManyToOne
    private ClassStudent classStudent;  
    
    public Student() {
    }
    public Student(String name, String email, String phone, String gender, String birthDate, String cpf, ClassStudent classStudent) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.classStudent = classStudent;               
    }
}
