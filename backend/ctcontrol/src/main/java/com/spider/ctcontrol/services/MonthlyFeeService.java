package com.spider.ctcontrol.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.Student;
import com.spider.ctcontrol.entities.dtos.MonthlyFeeDto;
import com.spider.ctcontrol.entities.enums.PaymentStatus;
import com.spider.ctcontrol.repositories.MonthlyFeeRepository;
import com.spider.ctcontrol.services.exceptions.InsertException;
import com.spider.ctcontrol.services.exceptions.MonthlyFeeCancelledException;
import com.spider.ctcontrol.services.exceptions.PaymentAlreadyException;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class MonthlyFeeService {

    private final MonthlyFeeRepository repository;
    private final StudentService studentService;

    public MonthlyFeeService(MonthlyFeeRepository repository, StudentService studentService) {
        this.repository = repository;
        this.studentService = studentService;
    }

    public List<MonthlyFee> findAll() {
        return repository.findAll();
    }

    public MonthlyFee findById(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Monthly fee, not found with "));
    }

    @Transactional
    public MonthlyFee enrollStudent(MonthlyFeeDto monthlyFeeDto, Long studentId) {
        Student student = studentService.findById(studentId);
        MonthlyFee monthly = new MonthlyFee(monthlyFeeDto);

        if (!student.isMonthlyFee(monthly)) 
           return update(monthly.getId(), monthly);
        
        return linkMonthlyFee(student, monthly);
    }

    @Transactional
    public MonthlyFee linkMonthlyFee(Student student, MonthlyFee monthly){
     
        monthly.setStudent(student);
        student.setMonthlyFee(monthly);

        return insert(monthly);
    }

   
    public MonthlyFee update(long id, MonthlyFee monthlyFeeDetails) {
        MonthlyFee monthlyFee = findById(id);
        monthlyFee.setStatus(monthlyFeeDetails.getStatus());
        monthlyFee.setAmount(monthlyFeeDetails.getAmount());
        monthlyFee.setDueDay(monthlyFeeDetails.getDueDay());

        return insert(monthlyFee);
    }   

    @Transactional
    public MonthlyFee payMonthlyFee(long id) {
        MonthlyFee monthlyFee = findById(id);
        

        if (monthlyFee.isPendingOverdue()) {
             monthlyFee.setStatus(PaymentStatus.PAID);
             monthlyFee.setLastPayment(LocalDate.now());
        } else if(monthlyFee.getStatus() == PaymentStatus.PAID){
            throw new PaymentAlreadyException("Monthly fee is already marked as PAID for student with ID: " + id);
        } else {
            throw new MonthlyFeeCancelledException(id, "Payment error, monthly fee cancelled.");
        }

        return insert(monthlyFee);
    }

    public MonthlyFee insert(MonthlyFee monthlyFee) {
        try {
            Objects.requireNonNull(monthlyFee, "Monthly fee must not be null");

            return repository.save(monthlyFee);
        } catch (Exception e) {
            throw new InsertException("Monthly fee");
        } 
    }

    @Transactional
    public void delete(long id) {
        MonthlyFee monthlyFee = findById(id);
            
        if (monthlyFee.isStudent()) {
           monthlyFee = unlinkStudent(monthlyFee);
        }
        Objects.requireNonNull(monthlyFee, "Monthly fee must not be null");
        repository.delete(monthlyFee);
    }

    public MonthlyFee unlinkStudent(MonthlyFee monthlyFee) {

        monthlyFee.getStudent().setMonthlyFee(null);
        monthlyFee.setStudent(null);

        return repository.save(monthlyFee);
    }
        
    @Transactional
    public void statusPending() {
        LocalDate today = LocalDate.now();
        Long dayToday = Long.valueOf(today.getDayOfMonth());

        List<MonthlyFee> fees = repository.findByDueDayAndStatus(dayToday, PaymentStatus.PAID);
    
        fees.forEach(fee -> {
            if(isPaidMonth(fee)){
                fee.setStatus(PaymentStatus.PENDING);   
            }
        });
        repository.saveAll(fees);
        
    }   

    public boolean isPaidMonth(MonthlyFee fee) {
        if(fee.getLastPayment() == null) return false;

        LocalDate lastPayment = fee.getLastPayment();
        LocalDate today = LocalDate.now();

        return lastPayment.getMonth() == today.getMonth() && lastPayment.getYear() == today.getYear();

    }

    @Transactional
    public void statusOverdue() {
        LocalDate deadline = LocalDate.now().minusDays(3);

        List<MonthlyFee> lateFees = repository.findLates(deadline.getDayOfMonth());
     
        lateFees.forEach(fee -> fee.setStatus(PaymentStatus.OVERDUE));
        
        repository.saveAll(lateFees);
    }

    public void notifyLatePayment(Long id) {
        MonthlyFee monthlyFee = findById(id);
        if (!monthlyFee.isPendingOverdue()) {
            // Logic to send notification (whatsapp) to the student
            System.out.println("Notification sent to student with ID: " + monthlyFee.getStudent().getId() + " about late payment.");
        } else {
            throw new PaymentAlreadyException("Monthly fee is not overdue for student with ID: " + monthlyFee.getStudent().getId());
        }   
    }
}
