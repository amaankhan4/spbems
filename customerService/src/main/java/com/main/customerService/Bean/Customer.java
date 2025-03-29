package com.main.customerService.Bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name="customers", uniqueConstraints = {
    @UniqueConstraint(columnNames = "ConsumerNumber"),
    @UniqueConstraint(columnNames = "Email"),
    @UniqueConstraint(columnNames = "UserId")
})
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Customer implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ConsumerNumber", nullable = false, unique = true, length = 13)
    @Size(min = 13, max = 13, message = "Consumer number must be exactly 13 digits")
    @Pattern(regexp = "\\d+",message = "Consumer number must contain only digits")
    private String consumerNumber;

    @Column(name = "FullName", nullable = false, length = 50)
    @Size(max = 50, message = "Name can be of maximum 50 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Name must contain only alphabets")
    private String fullName;

    @Column(name = "address", nullable = false)
    @Size(min = 10, message = "Address must be at least 10 characters long")
    private String address;


    @Column(name = "Email", nullable = false, unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "Mobile_Number", nullable = false, length = 10)
    @Size(min = 10, max = 10, message = "Mobile number must be exactly 10 digits")
    @Pattern(regexp = "^[6789](?!.*([0-9])\\1{3})[0-9]{9}$", message = "Invalid mobile number")
    private String mobileNumber;

    @Column(name="CustomerType", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Column(name = "ElectricalSection", nullable = false)
    @Enumerated(EnumType.STRING)
    private ElectricalSection electricalSection;

    @Column(name = "UserId", nullable = false, unique = true, length = 20)
    @Size(min = 5, max = 20, message = "User id must be between 5 and 20 characters")
    private String userId;

    @Column(name = "Password", nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$", message = "Password must contain at least one digit, one lowercase, one uppercase, one special character")
    private String password;

    @Column(name = "Status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public Customer(Long id, String consumerNumber, String fullName, String address, String email, String mobileNumber, CustomerType customerType, ElectricalSection electricalSection, String userId, String password, Status status) {
        this.id = id;
        this.consumerNumber = consumerNumber;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.customerType = customerType;
        this.electricalSection = electricalSection;
        this.userId = userId;
        this.password = password;
        this.status = status;
    }

    public enum CustomerType {
        RESIDENTIAL, COMMERCIAL
    }

    public enum ElectricalSection {
        OFFICE, REGIONAL
    }

    public enum Status {
        ACTIVE, INACTIVE
    }
}
