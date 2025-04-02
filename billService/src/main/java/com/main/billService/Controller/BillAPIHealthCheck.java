package com.main.billService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin("*")
public class BillAPIHealthCheck {
    @GetMapping("/")
    public String HealthCheck(){
        return "Health Check Passed.";
    }

}


