package com.main.paymentService.Bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_number")
    private Long invoiceNumber;

    @NotNull(message = "Payment ID is required")
    @Column(name = "payment_id", nullable = false, unique = true)
    private String paymentId;

    @NotNull(message = "Transaction ID is required")
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @NotNull(message = "Receipt Number is required")
    @Column(name = "receipt_number", nullable = false, unique = true)
    private String receiptNumber;

    @NotNull(message = "Consumer details are required")
    @Column(name = "consumer_details", nullable = false)
    private String consumerDetails;

    @NotNull(message = "Transaction date is required")
    @PastOrPresent(message = "Transaction date cannot be in the future")
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transaction type is required")
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @NotNull(message = "Bill Number is required")
    @Column(name = "bill_number", nullable = false, unique = true)
    private String billNumber;

    @NotNull(message = "Transaction amount is required")
    @Min(value = 0, message = "Transaction amount must be a positive value")
    @Column(name = "transaction_amount", nullable = false)
    private Double transactionAmount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transaction status is required")
    @Column(name = "transaction_status", nullable = false)
    private TransactionStatus transactionStatus;

    @PrePersist
    protected void onCreate() {
        this.transactionDate = LocalDateTime.now();
    }

    public enum TransactionType {
        CREDIT,
        DEBIT
    }

    public enum TransactionStatus {
        SUCCESS,
        PENDING,
        FAILED
    }
}
