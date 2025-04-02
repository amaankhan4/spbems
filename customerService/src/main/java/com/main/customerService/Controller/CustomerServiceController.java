package com.main.customerService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.main.customerService.Bean.Customer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.main.customerService.Service.CustomerService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin("*")
@RequestMapping("/customer")
public class CustomerServiceController {

	@Autowired
	private final CustomerService customerService;
	private final HttpSession session;

	public CustomerServiceController(CustomerService customerService, HttpSession session) {
		this.customerService = customerService;
		this.session = session;
	}

	@GetMapping(value = "/validate-session")
	public ResponseEntity<Map<String, Boolean>> validateSession(HttpSession session) {
		Map<String, Boolean> response = new HashMap<>();
		if (session.getAttribute("type") != null) {
			response.put("authenticated", true);
			return ResponseEntity.ok(response);
		}
		response.put("authenticated", false);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<Customer> register(@RequestBody Customer customer) {
		try {
			return ResponseEntity.ok(customerService.registerCustomer(customer));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(customer);
		}

	}

	@PostMapping(value = "/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Customer customer) {
		Map<String, String> response = new HashMap<>();
		try {
			if (customer.getEmail() == null) {
				customerService.loginCustomer(customer.getUserId(), customer.getPassword(), "userId");
				session.setAttribute("identifier", customer.getUserId());
				session.setAttribute("type", "userId");
			} else if (customer.getUserId() == null) {
				customerService.loginCustomer(customer.getEmail(), customer.getPassword(), "email");
				session.setAttribute("type", "userId");
				session.setAttribute("identifier", "email");
			}
		} catch (Exception e) {
			response.put("message", "Invalid credentials");
			return ResponseEntity.badRequest().body(response);
		}
		session.setAttribute("name", customer.getFullName());
		response.put("message", "Login successful");
		return ResponseEntity.ok(response);
	}
	
	 @GetMapping(value = "/me")
	    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
	        HttpSession session = request.getSession(false);
	        
	        if (session == null || session.getAttribute("type") == null) {
	            return ResponseEntity.status(401).body("User not logged in");
	        }

	        String identifier = (String) session.getAttribute("identifier");
	        String type = (String) session.getAttribute("type");
	        String name = (String) session.getAttribute("name");

	        return ResponseEntity.ok(new UserDTO(identifier, type, name));
	    }
	    public class UserDTO {
	        private String identifier;
	        private String type;
	        private String name;
	    
	        public UserDTO(String identifier, String type, String name) {
	            this.identifier = identifier;
	            this.type = type;
	            this.name = name;
	        }
	    
	        public String getIdentifier() { return identifier; }
	        public String getType() { return type; }
	        public String getName() { return name; }
	    }

	@GetMapping(value = "/getCustomerDetails")
	public ResponseEntity<Customer> getCustomerDetails(@RequestParam String userId) {
		if (userId == null) {
			userId = (String) session.getAttribute("userId");
			if (userId == null) {
				return ResponseEntity.badRequest().body(null);
			}
		}
		Customer customer = customerService.getCustomerDetails(userId);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customer);
	}
	
	 @PostMapping(value = "/logout")
	    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
	        Map<String, String> res = new HashMap<>();
	        HttpSession session = request.getSession(false);
	        if (session != null) {
	            session.invalidate(); // Destroy session
	        }
	        res.put("message", "Logout successful");
	        return ResponseEntity.ok(res);
	    }
	
	@PostMapping(value = "/update")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) {
		try {
			customerService.updateCustomerDetails((String) session.getAttribute("userId"), customer);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body("Invalid credentials");
		}
		return ResponseEntity.ok("Update successful");
	}

}

