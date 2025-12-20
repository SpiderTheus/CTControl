package com.spider.ctcontrol.config;

import java.time.LocalDate;
import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.spider.ctcontrol.entities.ClassStudent;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.Teacher;
import com.spider.ctcontrol.entities.User;
import com.spider.ctcontrol.entities.enums.Gender;
import com.spider.ctcontrol.entities.enums.PaymentStatus;
import com.spider.ctcontrol.repositories.ClassStudentRepository;

import com.spider.ctcontrol.repositories.MonthlyFeeRepository;
import com.spider.ctcontrol.repositories.StudentRepository;
import com.spider.ctcontrol.repositories.TeacherRepository;
import com.spider.ctcontrol.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;

@Configuration
@Profile("test")
@Transactional
public class testConfig implements CommandLineRunner {

    @Autowired
    private TeacherRepository teacherRepository;


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MonthlyFeeRepository monthlyFeeRepository;

    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        
        User user = new User("user", passwordEncoder.encode("password")); // password: password (BCrypt encoded at runtime)
        User user2 = new User("spider", passwordEncoder.encode("adminpass")); // password: adminpass (BCrypt encoded at runtime)

        userRepository.saveAll(Arrays.asList(user, user2));

        Teacher teacher1 = new Teacher("John Doe", "123234", user);
        Teacher teacher2 = new Teacher("Jane Smith", "123235", user2);

        teacherRepository.saveAll(Arrays.asList(teacher1, teacher2));


        Student student1 = new Student("Alice Johnson", "alice.johnson@example.com", "123456789", Gender.FEMALE, "2000-01-01", "123.456.789-00");
        Student student2 = new Student("Bob Brown", "bob.brown@example.com", "987654321", Gender.MALE, "1999-12-31", "987.654.321-00");
        Student student3 = new Student("Carlos Eduardo", "carlos.eduardo@example.com", "456789123", Gender.MALE, "2001-02-15", "456.789.123-00");

        studentRepository.saveAll(Arrays.asList(student1, student2, student3));

        ClassStudent classStudent1 = new ClassStudent(teacher1, 10, 100.0, "Mon, Wed, Fri", "Taekwondo");
        ClassStudent classStudent2 = new ClassStudent(teacher2, 15.30, 150.0, "Tue, Thu", "Taekwondo");


        classStudentRepository.saveAll(Arrays.asList(classStudent1, classStudent2));

        MonthlyFee fee1 = new MonthlyFee(student1, PaymentStatus.PAID, 70.0, 8, LocalDate.of(2024, 5, 8));
        MonthlyFee fee2 = new MonthlyFee(student2, PaymentStatus.PENDING, 80.0, 10, null);
        MonthlyFee fee3 = new MonthlyFee(student3, PaymentStatus.OVERDUE, 10.0, 15, null);

        
        monthlyFeeRepository.saveAll(Arrays.asList(fee1, fee2, fee3));

        Student s4 = new Student("Matheus", "email@email.com", "999999", Gender.MALE, "2000-01-01", "000.000.000-00");

        student1.setClassStudent(classStudent1);
        student1.setMonthlyFee(fee1);
        student2.setClassStudent(classStudent1);
        student2.setMonthlyFee(fee2);
        student3.setClassStudent(classStudent2);
        student3.setMonthlyFee(fee3);

        s4.setClassStudent(classStudent1);
        s4.setMonthlyFee(fee1);
        
        studentRepository.saveAll(Arrays.asList(student1, student2, student3, s4));
    }
}