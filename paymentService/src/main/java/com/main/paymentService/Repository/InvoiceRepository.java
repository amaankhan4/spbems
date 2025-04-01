package com.main.paymentService.Repository;

import com.main.paymentService.Bean.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByTransactionId(String transactionId);
}
