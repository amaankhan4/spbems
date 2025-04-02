package com.main.adminServices.Bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
//import java.sql.Timestamp;

@Entity
@Table(name = "admin", uniqueConstraints = { @UniqueConstraint(columnNames = "adminId") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminId;

	@Column(name = "Password", nullable = false)
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$", message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
	private String password;
	
	@Column(name = "FullName", nullable = false, length = 50)
    @Size(max = 50, message = "Name can be of maximum 50 characters")
    @Pattern(regexp = "[a-zA-Z ]+", message = "Name must contain only alphabets")
    private String fullName;
	
	@Column(name = "Email", nullable = false, unique = true)
    @Email(message = "Invalid email format")
    private String email;
	
	@Column(name = "MobileNumber", nullable = false, length = 10)
    @Size(min = 10, max = 10, message = "Mobile number must be exactly 10 digits")
    @Pattern(regexp = "^[6789](?!.*([0-9])\\1{3})[0-9]{9}$", message = "Invalid mobile number")
    private String mobileNumber;

}


