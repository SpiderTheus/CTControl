package com.spider.ctcontrol.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.spider.ctcontrol.entities.Teacher;
import com.spider.ctcontrol.repositories.TeacherRepository;

@Configuration
@Profile("test")
public class testConfig implements CommandLineRunner {

    @Autowired
    private TeacherRepository teacherRepository;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Executando testConfig...");
        Teacher teacher1 = new Teacher("John Doe", "john.doe@example.com", "123234");
        Teacher teacher2 = new Teacher("Jane Smith", "jane.smith@example.com", "123235");

        teacherRepository.saveAll(Arrays.asList(teacher1, teacher2));
    }
    
}
