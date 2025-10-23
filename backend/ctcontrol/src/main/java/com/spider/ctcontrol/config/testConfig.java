package com.spider.ctcontrol.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.spider.ctcontrol.entities.Modality;
import com.spider.ctcontrol.entities.Teacher;
import com.spider.ctcontrol.repositories.ModalityRepository;
import com.spider.ctcontrol.repositories.TeacherRepository;

import jakarta.transaction.Transactional;

@Configuration
@Profile("test")
@Transactional
public class testConfig implements CommandLineRunner {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ModalityRepository modalityRepository;

    @Override
    public void run(String... args) throws Exception {
        
        Teacher teacher1 = new Teacher("John Doe", "john.doe@example.com", "123234");
        Teacher teacher2 = new Teacher("Jane Smith", "jane.smith@example.com", "123235");

        teacherRepository.saveAll(Arrays.asList(teacher1, teacher2));

        Modality modality1 = new Modality("Taekwondo", teacher1);
        Modality modality2 = new Modality("Karate", teacher2);

        modalityRepository.saveAll(Arrays.asList(modality1, modality2));
    }
    
}
