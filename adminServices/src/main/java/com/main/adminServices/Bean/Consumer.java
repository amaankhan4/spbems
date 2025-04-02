package com.main.adminServices.Bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.io.Serializable;


@Entity
@Table(name="consumer")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Consumer implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CustomerId",nullable=false)
    private Long customerId;

    @Column(name = "ConsumerNumber", nullable = false, unique = true, length = 13)
    @Size(min = 13, max = 13, message = "Consumer number must be exactly 13 digits")
    @Pattern(regexp = "\\d+",message = "Consumer number must contain only digits")
    private String consumerNumber;

    @Column(name = "address", nullable = false)
    @Size(min = 10, message = "Address must be at least 10 characters long")
    private String address;

    @Column(name="CustomerType", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Column(name = "ElectricalSection", nullable = false)
    @Enumerated(EnumType.STRING)
    private ElectricalSection electricalSection;

    @Builder.Default
    @Column(name = "Status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;

    public enum CustomerType {
        RESIDENTIAL, COMMERCIAL
    }

    public enum ElectricalSection {
        OFFICE, REGIONAL
    }

    public enum Status {
        ACTIVE, INACTIVE
    }

    public Consumer(Long id, Long customerId, String consumerNumber, String address, CustomerType customerType, ElectricalSection electricalSection, Status status) {
        this.id = id;
        this.customerId = customerId;
        this.consumerNumber = consumerNumber;
        this.address = address;
        this.customerType = customerType;
        this.electricalSection = electricalSection;
        this.status = status;
    }
}

