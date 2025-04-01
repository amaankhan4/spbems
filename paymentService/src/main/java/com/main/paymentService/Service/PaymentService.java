package com.main.paymentService.Service;
import java.sql.Timestamp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.paymentService.Bean.Payment;
import com.main.paymentService.Repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository transactionRepository;

    public Payment createPayment(Payment Payment) {
        Payment.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        return transactionRepository.save(Payment);
    }

    public Optional<Payment> getTransactionById(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }
}
