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

    @PostMapping(value="/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer){
        try{
            return ResponseEntity.ok(customerService.registerCustomer(customer));
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(customer);
        }

    }

    @PostMapping(value="/login")
    public ResponseEntity<String> login(@RequestBody Customer customer){

        try{
            customerService.loginCustomer(customer.getUserId(), customer.getPassword());
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        session.setAttribute("userId", customer.getUserId());
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping(value="/logout")
    public ResponseEntity<String> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }


    @GetMapping(value="/getCustomer-details")
    public ResponseEntity<Customer> getCustomerDetails(@RequestParam String userId){
        if(userId == null){
            userId = (String) session.getAttribute("userId");
            if(userId == null){
                return ResponseEntity.badRequest().body(null);
            }
        }
        Customer customer = customerService.getCustomerDetails(userId);
        if(customer == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping(value="/update")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer){
        try{
            customerService.updateCustomerDetails((String) session.getAttribute("userId"),customer);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        return ResponseEntity.ok("Update successful");
    }
    
}

