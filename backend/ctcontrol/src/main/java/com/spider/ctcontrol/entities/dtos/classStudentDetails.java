package com.spider.ctcontrol.entities.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class classStudentDetails {
   
    private double time;
    private Double costMonthly;
    private String daysWeek;
    private String modality;

    public classStudentDetails() {
    }

    public classStudentDetails(double time, Double costMonthly, String daysWeek,
            String modality) {
  
        this.time = time;
        this.costMonthly = costMonthly;
        this.daysWeek = daysWeek;
        this.modality = modality;
    }
}
