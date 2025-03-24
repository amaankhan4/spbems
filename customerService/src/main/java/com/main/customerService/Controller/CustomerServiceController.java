package com.main.customerService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerServiceController {

    @GetMapping("/")
    public String HealthCheck(){
        return "Health Check Passed.";
    }
}

