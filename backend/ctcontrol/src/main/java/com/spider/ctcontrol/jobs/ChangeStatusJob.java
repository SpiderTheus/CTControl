package com.spider.ctcontrol.jobs;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.spider.ctcontrol.services.MonthlyFeeService;

import org.springframework.lang.NonNull;

@Component
public class ChangeStatusJob extends QuartzJobBean {

    @Autowired
    private MonthlyFeeService monthlyFeeService;
    

    @Override
    protected void executeInternal(@NonNull JobExecutionContext context) throws JobExecutionException {
     System.out.println("Iniciando Job de verificação de pagamentos...");
          
        monthlyFeeService.statusPending();
        monthlyFeeService.statusOverdue();

        System.out.println("Job de verificação de pagamentos concluído.");
    }

}
