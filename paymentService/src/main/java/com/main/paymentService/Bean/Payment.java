package com.main.paymentService.Bean;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments", uniqueConstraints = {@UniqueConstraint(columnNames = "PaymentID")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment implements Serializable {

	private static final long serialVersionUID = -5419451328216904145L;

	@Column(name = "PaymentID", nullable = false, unique = true, length = 50)
    @Size(max = 50, message = "Payment ID can be a maximum of 50 characters")
    private String paymentId;

    @Column(name = "TransactionID", nullable = false, length = 50)
    @Size(max = 50, message = "Transaction ID can be a maximum of 50 characters")
    private String transactionId;

    @Column(name = "ReceiptNumber", nullable = false)
    private Long receiptNumber;

    @Column(name = "TransactionDate", nullable = false)
    private Timestamp transactionDate;

    @Column(name = "TransactionType", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "TransactionAmount", nullable = false)
    private Float transactionAmount;

    @Column(name = "TransactionStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(name = "BillNumber", nullable = false)
    private Long billNumber;

    public enum TransactionType {
        CREDIT, DEBIT
    }

    public enum TransactionStatus {
        SUCCESS, FAILED, PENDING
    }
}
