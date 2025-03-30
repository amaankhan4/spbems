package com.main.customerService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin("*")
public class CustomerAPIHealthCheck {
    @GetMapping("/")
    public String HealthCheck(){
        return "Health Check Passed.";
    }

}
