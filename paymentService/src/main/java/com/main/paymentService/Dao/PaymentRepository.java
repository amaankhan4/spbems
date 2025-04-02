package com.main.paymentService.Dao;

import com.main.paymentService.Bean.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    
}




