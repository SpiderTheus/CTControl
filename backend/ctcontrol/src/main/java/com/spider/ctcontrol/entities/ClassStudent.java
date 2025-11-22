package com.spider.ctcontrol.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

    @ManyToOne
    private Teacher teacher;

    private double time;

    private Double costMonthly;

    private String daysWeek;

    private String modality;

    @OneToMany(mappedBy = "classStudent", fetch = FetchType.LAZY)
    private Set<Student> students;

    @ManyToOne
    private MonthlyFee monthlyFee;

     private String denomination;

    public ClassStudent() {
    }

    public ClassStudent(Teacher teacher, double time, double costMonthly, String daysWeek, String modality,
            Set<Student> students) {
        this.teacher = teacher;
        this.time = time;
        this.costMonthly = costMonthly;
        this.daysWeek = daysWeek;
        this.modality = modality;
        this.students = students;
        this.denomination = modality + " - " + teacher.getUsername();
    }
}
