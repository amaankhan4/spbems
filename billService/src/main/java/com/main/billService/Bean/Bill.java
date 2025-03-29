package com.main.billService.Bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "bills", uniqueConstraints = {
        @UniqueConstraint(columnNames = "BillNumber")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill implements Serializable {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(Long billNumber) {
		this.billNumber = billNumber;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public ConnectionType getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}

	public ConnectionStatus getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(ConnectionStatus connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public String getBillPeriod() {
		return billPeriod;
	}

	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}

	public Timestamp getBillDate() {
		return billDate;
	}

	public void setBillDate(Timestamp billDate) {
		this.billDate = billDate;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getDisconnectionDate() {
		return disconnectionDate;
	}

	public void setDisconnectionDate(Timestamp disconnectionDate) {
		this.disconnectionDate = disconnectionDate;
	}

	public Float getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(Float dueAmount) {
		this.dueAmount = dueAmount;
	}

	public Float getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Float payableAmount) {
		this.payableAmount = payableAmount;
	}

	public String getConsumerNumber() {
		return consumerNumber;
	}

	public void setConsumerNumber(String consumerNumber) {
		this.consumerNumber = consumerNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BillNumber", nullable = false, unique = true)
    @NotNull(message = "Bill number cannot be null")
    private Long billNumber;

    @Column(name = "PaymentStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "ConnectionType", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConnectionType connectionType;

    @Column(name = "ConnectionStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConnectionStatus connectionStatus;

    @Column(name = "BillPeriod", nullable = false)
    private String billPeriod; // JSON format {"start":Timestamp, "end":Timestamp}

    @Column(name = "BillDate", nullable = false)
    private Timestamp billDate;

    @Column(name = "DueDate", nullable = false)
    private Timestamp dueDate;

    @Column(name = "DisconnectionDate")
    private Timestamp disconnectionDate;

    @Column(name = "DueAmount", nullable = false)
    @DecimalMin(value = "0.0", message = "Due amount must be positive")
    private Float dueAmount;

    @Column(name = "PayableAmount", nullable = false)
    @DecimalMin(value = "0.0", message = "Payable amount must be positive")
    private Float payableAmount;

    @Column(name = "ConsumerNumber", nullable = false, length = 13)
    @Size(min = 13, max = 13, message = "Consumer number must be exactly 13 digits")
    @Pattern(regexp = "\\d+", message = "Consumer number must contain only digits")
    private String consumerNumber;

    public enum PaymentStatus {
        PAID, UNPAID, PENDING
    }

    public enum ConnectionType {
        RESIDENTIAL, COMMERCIAL
    }

    public enum ConnectionStatus {
        ACTIVE, INACTIVE, DISCONNECTED
    }
}