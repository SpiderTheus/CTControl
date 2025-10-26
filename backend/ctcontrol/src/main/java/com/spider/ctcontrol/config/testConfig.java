package com.spider.ctcontrol.config;

import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.spider.ctcontrol.entities.ClassStudent;
import com.spider.ctcontrol.entities.Modality;
import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.Teacher;
import com.spider.ctcontrol.entities.enums.Gender;
import com.spider.ctcontrol.entities.enums.PaymentStatus;
import com.spider.ctcontrol.repositories.ClassStudentRepository;
import com.spider.ctcontrol.repositories.ModalityRepository;
import com.spider.ctcontrol.repositories.MonthlyFeeRepository;
import com.spider.ctcontrol.repositories.StudentRepository;
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

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MonthlyFeeRepository monthlyFeeRepository;

    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Override
    public void run(String... args) throws Exception {
        
        Teacher teacher1 = new Teacher("John Doe", "john.doe@example.com", "123234");
        Teacher teacher2 = new Teacher("Jane Smith", "jane.smith@example.com", "123235");

        teacherRepository.saveAll(Arrays.asList(teacher1, teacher2));

        Modality modality1 = new Modality("Taekwondo", teacher1);
        Modality modality2 = new Modality("Karate", teacher2);

        modalityRepository.saveAll(Arrays.asList(modality1, modality2));

        Student student1 = new Student("Alice Johnson", "alice.johnson@example.com", "123456789", Gender.FEMALE, "2000-01-01", "123.456.789-00");
        Student student2 = new Student("Bob Brown", "bob.brown@example.com", "987654321", Gender.MALE, "1999-12-31", "987.654.321-00");
        Student student3 = new Student("Carlos Eduardo", "carlos.eduardo@example.com", "456789123", Gender.MALE, "2001-02-15", "456.789.123-00");

        studentRepository.saveAll(Arrays.asList(student1, student2, student3));

        ClassStudent classStudent1 = new ClassStudent(10, 100.0, "Mon, Wed, Fri", modality1, Set.of(student1, student2));
        ClassStudent classStudent2 = new ClassStudent(15.30, 150.0, "Tue, Thu", modality2, Set.of(student3));

        classStudentRepository.saveAll(Arrays.asList(classStudent1, classStudent2));

        MonthlyFee fee1 = new MonthlyFee( student1, PaymentStatus.PAID, 5, Set.of(classStudent1));
        MonthlyFee fee2 = new MonthlyFee(student2, PaymentStatus.PENDING, 5, Set.of(classStudent1));
        MonthlyFee fee3 = new MonthlyFee(student3, PaymentStatus.OVERDUE, 10, Set.of(classStudent2));

        monthlyFeeRepository.saveAll(Arrays.asList(fee1, fee2, fee3));

        Student s = new Student("Matheus", "email@email.com", "999999", Gender.MALE, "2000-01-01", "000.000.000-00");

        ClassStudent turma = classStudentRepository.findById(1L).get();
        MonthlyFee mensalidade = monthlyFeeRepository.findById(1L).get();

        s.setClassStudent(turma);
        s.setMonthlyFee(mensalidade);

        studentRepository.save(s);

            }
}