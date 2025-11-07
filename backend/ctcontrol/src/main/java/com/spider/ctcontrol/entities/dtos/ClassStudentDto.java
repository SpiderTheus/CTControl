package com.spider.ctcontrol.entities.dtos;

import com.spider.ctcontrol.entities.ClassStudent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassStudentDto {
    private String denomination;
    private String teacher;
    private double time;
    private Double costMonthly;
    private String daysWeek;
    private String modality;

    private Integer studentsQtd;

    public ClassStudentDto() {
    }

    public ClassStudentDto(ClassStudent classStudent) {
        this.denomination = classStudent.getDenomination();
        this.teacher = classStudent.getTeacher().getUsername();
        this.time = classStudent.getTime();
        this.costMonthly = classStudent.getCostMonthly();
        this.daysWeek = classStudent.getDaysWeek();
        this.modality = classStudent.getModality().getName();
        this.studentsQtd = classStudent.getStudents().size();

    }
}
