package com.spider.ctcontrol.entities;

import java.util.Set;

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
public class ClassStudent implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double time;

    @ManyToOne
    private Modality modality;

    @OneToMany(mappedBy = "classStudent")
    private Set<Student> students;

    public ClassStudent() {
    }

    public ClassStudent(double time, Modality modality) {
        this.time = time;
        this.modality = modality;
    }

}
