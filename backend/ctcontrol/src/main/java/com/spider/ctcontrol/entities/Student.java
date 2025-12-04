package com.spider.ctcontrol.entities;

import com.spider.ctcontrol.entities.enums.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_students")
public class Student implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private String birthDate;
    private String cpf; 

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "monthly_fee_id", unique = true)
    private MonthlyFee monthlyFee;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_student_id")
    private ClassStudent classStudent;
    
    public Student() {
    }
    public Student(String name, String email, String phone, Gender gender, String birthDate, String cpf) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
        this.cpf = cpf; 
    }
}
