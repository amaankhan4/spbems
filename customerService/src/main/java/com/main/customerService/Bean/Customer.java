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

    // Multiple consumer no to customer check user story - 11,12

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CustomerId;


    @Column(name = "FullName", nullable = false, length = 50)
    @Size(max = 50, message = "Name can be of maximum 50 characters")
    @Pattern(regexp = "[a-zA-Z ]+", message = "Name must contain only alphabets")
    private String fullName;

    @Column(name = "Email", nullable = false, unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "Mobile_Number", nullable = false, length = 10)
    @Size(min = 10, max = 10, message = "Mobile number must be exactly 10 digits")
    @Pattern(regexp = "^[6789](?!.*([0-9])\\1{3})[0-9]{9}$", message = "Invalid mobile number")
    private String mobileNumber;

    @Column(name = "UserId", nullable = false, unique = true, length = 20)
    @Size(min = 5, max = 20, message = "User id must be between 5 and 20 characters")
    private String userId;

    @Column(name = "Password", nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$", 
            message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
    private String password;


    public Customer(Long CustomerId, String fullName, String email, String mobileNumber, String userId, String password) {
        this.CustomerId = CustomerId;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.userId = userId;
        this.password = password;
    }
    
    public Object map(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }
}
