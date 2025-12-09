package com.spider.ctcontrol.services;

import java.util.List;


import org.springframework.stereotype.Service;

import com.spider.ctcontrol.entities.MonthlyFee;
import com.spider.ctcontrol.entities.enums.PaymentStatus;
import com.spider.ctcontrol.repositories.MonthlyFeeRepository;
import com.spider.ctcontrol.services.exceptions.PaymentAlreadyException;
import com.spider.ctcontrol.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class MonthlyFeeService {

    private final MonthlyFeeRepository repository;


    public MonthlyFeeService(MonthlyFeeRepository repository) {
        this.repository = repository;
       
    }

    public List<MonthlyFee> findAll() {
        return repository.findAll();
    }

    public MonthlyFee findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Monthly fee, not found with "));
    }

    public MonthlyFee insert(MonthlyFee monthlyFee) {
        try {
            return repository.save(monthlyFee);
        } catch (Exception e) {
            throw new RuntimeException("Error inserting monthly fee: " + e.getMessage());
        } 
    }

    public MonthlyFee update(Long id, MonthlyFee monthlyFeeDetails) {
        MonthlyFee monthlyFee = findById(id);
        monthlyFee.setStatus(monthlyFeeDetails.getStatus());
        monthlyFee.setAmount(monthlyFeeDetails.getAmount());
        monthlyFee.setDueDay(monthlyFeeDetails.getDueDay());

        return insert(monthlyFee);
    }   

    public void delete(Long id) {
        MonthlyFee monthlyFee = findById(id);
        repository.delete(monthlyFee);
    }

  
    public MonthlyFee statusPaid(Long id) {
        MonthlyFee monthlyFee = findById(id);
        

        if (monthlyFee.getStatus() != PaymentStatus.PAID) {
             monthlyFee.setStatus(PaymentStatus.PAID);
        } else {
            throw new PaymentAlreadyException("Monthly fee is already marked as PAID for student with ID: " + id);
        }

        return insert(monthlyFee);
    }

    @Transactional
    public void statusPending() {
        List<MonthlyFee> fees = repository.findAll();
        for (MonthlyFee fee : fees) {
            if (fee.getStatus() == PaymentStatus.PAID) {
                fee.setStatus(PaymentStatus.PENDING);
                repository.save(fee);
            }
        }
    }   



    public void notifyLatePayment(Long id) {
        MonthlyFee monthlyFee = findById(id);
        if (monthlyFee.getStatus() == PaymentStatus.OVERDUE) {
            // Logic to send notification (whatsapp) to the student
            System.out.println("Notification sent to student with ID: " + monthlyFee.getStudent().getId() + " about late payment.");
        } else {
            throw new PaymentAlreadyException("Monthly fee is not overdue for student with ID: " + monthlyFee.getStudent().getId());
        }   
    }

    public List<MonthlyFee> findByStatus(String status) {
        return repository.findByStatus(PaymentStatus.valueOf(status.toUpperCase()));
    }   

}
