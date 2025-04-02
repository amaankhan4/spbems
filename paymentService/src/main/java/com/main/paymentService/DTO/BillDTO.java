package com.main.paymentService.DTO;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class BillDTO {
    private Long billNumber;
    private PaymentStatus paymentStatus;
    private ConnectionType connectionType;
    private ConnectionStatus connectionStatus;
    private String billPeriod;
    private Timestamp billDate;
    private Timestamp dueDate;
    private Timestamp disconnectionDate;
    private Float dueAmount;
    private Float payableAmount;
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
