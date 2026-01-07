package com.spider.ctcontrol.entities.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassStudentDetails {
   
    private double time;
    private Double costMonthly;
    private String daysWeek;
    private String modality;

    public ClassStudentDetails() {
    }

    public ClassStudentDetails(double time, Double costMonthly, String daysWeek,
            String modality) {
  
        this.time = time;
        this.costMonthly = costMonthly;
        this.daysWeek = daysWeek;
        this.modality = modality;
    }
}
