package com.spider.ctcontrol.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ClassStudent implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double time;

    private String daysWeek;

    @ManyToOne
    private Modality modality;

    @OneToMany
    private Set<Student> students;

    @ManyToOne
    private MonthlyFee monthlyFee;

    public ClassStudent() {
    }

    public ClassStudent(double time, String daysWeek, Modality modality, Set<Student> students) {
        this.time = time;
        this.daysWeek = daysWeek;
        this.modality = modality;
        this.students = students;
    }

}
