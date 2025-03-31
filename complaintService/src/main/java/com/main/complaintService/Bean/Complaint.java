package com.main.complaintService.Bean;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "complaints", uniqueConstraints={
        @UniqueConstraint(columnNames = "consumerNumber"),
        @UniqueConstraint(columnNames = "customerId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complaintId;

    @Column(name = "consumerNumber", nullable = false, length = 13, unique = true)
    @Size(min = 13, max = 13, message = "Consumer number must be exactly 13 digits")
    @Pattern(regexp = "\\d+", message = "Consumer number must contain only digits")
    private String consumerNumber;

    @Column(name = "customerId", nullable = false)
    private Long customerId;

    @Column(name = "complaintType", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplaintType complaintType;

    @Column(name = "description", nullable = false, length = 500)
    @Size(min = 10, max = 500, message = "Complaint description must be between 10 and 500 characters")
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplaintPriority priority;

    @Column(name = "registeredDate", nullable = false, updatable = false)
    private LocalDateTime registeredDate;

    @Column(name = "resolvedDate")
    private LocalDateTime resolvedDate;
    
    @PrePersist
    protected void onCreate() {
        this.registeredDate = LocalDateTime.now();
        this.status = StatusType.PENDING;
    }

    public enum ComplaintType {
        POWER_OUTAGE,
        BILLING_ISSUE,
        METER_FAULT,
        CONNECTION_PROBLEM,
        OTHER
    }
    
    public enum ComplaintPriority {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }
    
    public Complaint(Long complaintId, String consumerNumber, Long customerId, ComplaintType complaintType, String description, ComplaintPriority priority,StatusType status) {
        this.complaintId = complaintId;
        this.consumerNumber = consumerNumber;
        this.customerId = customerId;
        this.complaintType = complaintType;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }
    
}
