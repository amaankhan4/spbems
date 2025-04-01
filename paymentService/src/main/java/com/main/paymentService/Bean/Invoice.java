package com.main.paymentService.Bean;
import com.main.paymentService.Bean.Payment;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "Invoice", uniqueConstraints = {
    @UniqueConstraint(columnNames = "InvoiceNumber"),
    @UniqueConstraint(columnNames = "TransactionID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Invoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "InvoiceNumber", nullable = false, unique = true, length = 50)
    @Size(max = 50, message = "Invoice Number can be a maximum of 50 characters")
    private String invoiceNumber;

    @Column(name = "PaymentID", nullable = false, length = 50)
    @Size(max = 50, message = "Payment ID can be a maximum of 50 characters")
    private String paymentId;

    @Column(name = "TransactionID", nullable = false, unique = true, length = 50)
    @Size(max = 50, message = "Transaction ID can be a maximum of 50 characters")
    private String transactionId;

    @Column(name = "ReceiptNumber", nullable = false)
    private Long receiptNumber;

//    @Column(name = "ConsumerDetails", nullable = false, length = 255)
//    @NotBlank(message = "Consumer details are required")
//    private String consumerDetails;

    @Column(name = "TransactionDate", nullable = false)
    private Timestamp transactionDate;

    @Column(name = "TransactionType", nullable = false)
    @Enumerated(EnumType.STRING)
    private com.main.paymentService.Bean.Payment.TransactionType transactionType;

    @Column(name = "BillNumber", nullable = false)
    private Long billNumber;

    @Column(name = "TransactionAmount", nullable = false)
    @Positive(message = "Transaction Amount must be positive")
    private Float transactionAmount;

    @Column(name = "TransactionStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private com.main.paymentService.Bean.Payment.TransactionStatus transactionStatus;
    
    @PrePersist
    public void generateInvoiceNumber() {
        if (this.invoiceNumber == null) {
            this.invoiceNumber = generateUniqueInvoiceNumber();
        }
    }
    
    Payment py = new Payment();

    private String generateUniqueInvoiceNumber() {
        return "INV-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
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

//    public String getConsumerDetails() {
//        return consumerDetails;
//    }
//
//    public void setConsumerDetails(String consumerDetails) {
//        this.consumerDetails = consumerDetails;
//    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public com.main.paymentService.Bean.Payment.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(com.main.paymentService.Bean.Payment.TransactionType transactionType2) {
        this.transactionType = transactionType2;
    }

    public Long getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(Long billNumber) {
        this.billNumber = billNumber;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public com.main.paymentService.Bean.Payment.TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(com.main.paymentService.Bean.Payment.TransactionStatus transactionStatus2) {
        this.transactionStatus = transactionStatus2;
    }

    
    
    
    public enum TransactionType {
        CREDIT, DEBIT
    }

    public enum TransactionStatus {
        SUCCESS, FAILED, PENDING
    }

	public void setGeneratedDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}
}
