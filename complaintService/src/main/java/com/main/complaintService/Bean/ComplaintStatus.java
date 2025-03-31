package com.main.complaintService.Bean;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "complaint-status")
@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @ManyToOne
    @JoinColumn(name = "complaintId", nullable = false)
    private Complaint complaint;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Column(name = "updatedBy", length = 50)
    private String updatedBy;

    @Column(name = "updatedOn", nullable = false)
    private LocalDateTime updatedOn;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @PrePersist
    protected void onCreate() {
        this.updatedOn = LocalDateTime.now();
    }

}
