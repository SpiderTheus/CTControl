package com.spider.ctcontrol.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tb_teachers")
@Getter
@Setter
public class Teacher {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usename;
    private String email;
    private String password;

    //@OneToMany(mappedBy = "teacher")
    //private HashSet<Modalidade> modalidades;

    public Teacher() {
    }

    public Teacher(String usename, String email, String password) {
        this.usename = usename;
        this.email = email;
        this.password = password;
    }
}
