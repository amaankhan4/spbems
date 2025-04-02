package com.main.paymentService.Bean;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="Payment", uniqueConstraints = {
    @UniqueConstraint(columnNames = "PaymentID"),
    @UniqueConstraint(columnNames = "TransactionID"),
    @UniqueConstraint(columnNames = "ReceiptNumber")
})
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Payment implements Serializable {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PaymentID", nullable = false, unique = true, length = 50)
    @Size(max = 50, message = "Payment ID can be a maximum of 50 characters")
    private String paymentId;
    
    @Column(name = "userId", nullable = false, unique = true, length = 50)
    @Size(max = 50)
    private String userId;
    
    @Column(name = "EMAIL", nullable = false, unique = true, length = 50)
    @Size(max = 50)
    private String email;

    @Column(name = "TransactionID", nullable = false, unique = true, length = 50)
    @Size(max = 50, message = "Transaction ID can be a maximum of 50 characters")
    private String transactionId;

    @Column(name = "ReceiptNumber", nullable = false, unique = true)
    @NotNull(message = "Receipt Number is required")
    private Long receiptNumber;

    @Column(name = "TransactionDate", nullable = false)
    @NotNull(message = "Transaction Date is required")
    private Timestamp transactionDate;

    @Column(name = "TransactionType", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "TransactionAmount", nullable = false)
    @NotNull(message = "Transaction Amount is required")
    @Positive(message = "Transaction Amount must be positive")
    private Float transactionAmount;

    @Column(name = "TransactionStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(name = "BillNumber", nullable = false)
    @NotNull(message = "Bill Number is required")
    private Long billNumber;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public Long getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(Long receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Long getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(Long billNumber) {
		this.billNumber = billNumber;
	}

	
	 public Payment() {
	    }
	
	
	public Payment(Long id, String paymentId,String userID,String email, String transactionId, Long receiptNumber, Timestamp transactionDate, 
                       TransactionType transactionType, Float transactionAmount, TransactionStatus transactionStatus, 
                       Long billNumber) {
        this.id = id;
        this.paymentId = paymentId;
        this.userId =  userID;
        this.email = email;
        this.transactionId = transactionId;
        this.receiptNumber = receiptNumber;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionStatus = transactionStatus;
        this.billNumber = billNumber;
    }
	
	
	 @PrePersist
	    public void generateIds() {
	        if (this.paymentId == null) {
	            this.paymentId = generatePaymentId();
	        }
	        if (this.transactionId == null) {
	            this.transactionId = generateTransactionId();
	        }
	    }

	    private String generatePaymentId() {
	        return "PAY-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
	    }

	    private String generateTransactionId() {
	        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
	    }
	
	
    public enum TransactionType {
        CREDIT, DEBIT
    }

    public enum TransactionStatus {
        SUCCESS, FAILED, PENDING
    }
}
