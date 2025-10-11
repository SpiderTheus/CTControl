package com.spider.ctcontrol.entities;

import javax.annotation.processing.Generated;

import org.hibernate.annotations.SecondaryRow;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
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
public class ClassGroup {
    static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double time;

    @ManyToOne
    private Modality modality;

    //@OneToMany(mappedBy = "classGroup")
    //private List<Student> students;

    public ClassGroup() {
    }

    public ClassGroup(double time, Modality modality) {
        this.time = time;
        this.modality = modality;
    }

 
}
