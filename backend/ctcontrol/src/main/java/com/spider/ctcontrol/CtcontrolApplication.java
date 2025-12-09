package com.spider.ctcontrol;



import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import com.spider.ctcontrol.jobs.ChangeStatusJob;


@SpringBootApplication
public class CtcontrolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CtcontrolApplication.class, args);
	}

	@Bean
    public JobDetail changeStatusJobDetail() {
        return JobBuilder.newJob(ChangeStatusJob.class)
                .withIdentity("changeStatus", "groupMonthlyFee")
                .storeDurably() 
                .build();
    }

	@Bean
    public Trigger chanrgeStatusTrigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("triggerChangeStatus", "groupMonthlyFee")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 21 23 * * ?")) 
                .build();
    }

	
}
