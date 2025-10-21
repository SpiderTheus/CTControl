package com.spider.ctcontrol.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_modalities")
@Getter
@Setter
public class Modality implements java.io.Serializable {
   private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    
    @ManyToOne
    private Teacher teacher;
    
    @OneToMany(mappedBy = "modality")
    private transient Set<ClassStudent> classStudents;

    public Modality() {
    }

    public Modality(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }
}
