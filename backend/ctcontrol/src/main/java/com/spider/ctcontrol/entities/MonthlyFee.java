package com.spider.ctcontrol.entities;

import org.hibernate.annotations.ManyToAny;

import com.spider.ctcontrol.entities.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_monthly_fees")
public class MonthlyFee {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@OneToOne
    //private Aluno aluno;
    
    @ManyToOne
    private Modality modality;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private int dueDay;

    public MonthlyFee() {
    }

    public MonthlyFee(Modality modality, Double amount, PaymentStatus status, int dueDay) {
        this.modality = modality;
        this.amount = amount;
        this.status = status;
        this.dueDay = dueDay;
    }
}
