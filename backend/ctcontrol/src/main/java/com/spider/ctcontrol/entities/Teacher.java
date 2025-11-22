package com.spider.ctcontrol.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tb_teachers")
@Getter
@Setter
public class Teacher implements java.io.Serializable {
   private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "teacher")
    @JoinColumn(name = "class_students_id")
    private transient Set<ClassStudent> classStudents;

    public Teacher() {
    }

    public Teacher(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
