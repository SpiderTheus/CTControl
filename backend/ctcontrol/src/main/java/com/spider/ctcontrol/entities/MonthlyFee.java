package com.spider.ctcontrol.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spider.ctcontrol.entities.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_monthly_fees")
public class MonthlyFee implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", unique = true)
    @JsonIgnore
    private Student student;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Double amount;

    private int dueDay;

    public MonthlyFee() {
    }

    public MonthlyFee(Student student, PaymentStatus status, Double amount, int dueDay) {
        this.student = student;
        this.status = status;
        this.amount = amount;
        this.dueDay = dueDay; 
    }
}
