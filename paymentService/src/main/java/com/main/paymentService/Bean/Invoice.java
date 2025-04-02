package com.main.paymentService.Bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    private Long receiptNumber;

//    @NotNull(message = "Consumer number is required")
//    @Column(name = "consumerNumber", nullable = false)
//    private String consumerNumber;

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
    
//    @Column(name = "ConsumerNumber", nullable = false, unique = true, length = 13)
//    @Size(min = 13, max = 13, message = "Consumer number must be exactly 13 digits")
//    @Pattern(regexp = "\\d+",message = "Consumer number must contain only digits")
//    private String consumerNumber;

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

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Long getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(Long receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

//	public String getConsumerNumber() {
//		return consumerNumber;
//	}
//
//	public void setConsumerNumber(String consumerNumber) {
//		this.consumerNumber = consumerNumber;
//	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
}




