package com.main.adminServices.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;

public class Bill {

    @JsonProperty("billNumber")
    private Long billNumber;

    @JsonProperty("paymentStatus")
    private PaymentStatus paymentStatus;

    @JsonProperty("connectionType")
    private ConnectionType connectionType;

    @JsonProperty("connectionStatus")
    private ConnectionStatus connectionStatus;

    @JsonProperty("billPeriod")
    private String billPeriod;

    @JsonProperty("billDate")
    private Timestamp billDate;

    @JsonProperty("dueDate")
    private Timestamp dueDate;

    @JsonProperty("disconnectionDate")
    private Timestamp disconnectionDate;

    @JsonProperty("dueAmount")
    private Float dueAmount;

    @JsonProperty("payableAmount")
    private Float payableAmount;

    @JsonProperty("consumerNumber")
    private Long consumerNumber;

    public enum PaymentStatus {
        PAID, UNPAID, PENDING
    }

    public enum ConnectionType {
        RESIDENTIAL, COMMERCIAL
    }

    public enum ConnectionStatus {
        ACTIVE, INACTIVE, DISCONNECTED
    }

    // ✅ Add Getters and Setters
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

    public Long getConsumerNumber() {
        return consumerNumber;
    }

    public void setConsumerNumber(Long consumerNumber) {
        this.consumerNumber = consumerNumber;
    }
}
