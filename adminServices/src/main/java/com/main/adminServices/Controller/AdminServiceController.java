package com.main.adminServices.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.main.adminServices.Bean.Consumer;
import org.springframework.web.bind.annotation.PostMapping;
import com.main.adminServices.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.main.adminServices.Bean.Admin;
import com.main.adminServices.Bean.Consumer.Status;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.main.adminServices.Dto.Bill;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminServiceController{
    @Autowired
    private final AdminService adminService;
    @Autowired
    private final HttpSession session;

    public AdminServiceController(AdminService adminService, HttpSession session) {
        this.adminService = adminService;
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

    @PostMapping(value = "/activate-consumer")
    public ResponseEntity<Consumer> activateConsumer(@RequestBody String consumerNumber) {
        if(session.getAttribute("isAdmin") == null || !(Boolean) session.getAttribute("isAdmin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try{
        return ResponseEntity.ok(adminService.activateConsumer(consumerNumber));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value = "/register-consumer")
    public ResponseEntity<Consumer> registerConsumer(@RequestBody Consumer consumer) {
        try{
            if(session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin")) {
                consumer.setStatus(Status.ACTIVE);
            }else{
                consumer.setStatus(Status.INACTIVE);
            }
            return ResponseEntity.ok(adminService.registerConsumer(consumer));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value = "admin-register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.registerAdmin(admin));
    }

    @PostMapping(value = "admin-login")
    public ResponseEntity<Admin> adminLogin(@RequestBody Admin admin) {
        Admin response = null;
        try{
            response = adminService.adminLogin(admin.getEmail(), admin.getPassword());
            if(response != null){
                session.setAttribute("isAdmin", true);
                session.setAttribute("type", "email");
                session.setAttribute("identifier", response.getEmail());
                return ResponseEntity.ok(response);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @GetMapping("/consumers/{customerId}")
    public ResponseEntity<List<Consumer>> getConsumerByCustomerId(@PathVariable Long customerId) {
        // if(session.getAttribute("isAdmin") == null || !(Boolean) session.getAttribute("isAdmin")) {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        // }
        return ResponseEntity.ok(adminService.getConsumerByCustomerId(customerId));
    }

    @PostMapping(value = "/update-consumer")
    public ResponseEntity<Consumer> updateConsumer(@RequestBody Consumer consumer) {
        if(session.getAttribute("isAdmin") == null || !(Boolean) session.getAttribute("isAdmin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(adminService.updateConsumer(consumer));
    }

    @GetMapping("/get-bill/{consumerNumber}")
    public ResponseEntity<Bill> getBillByConsumerNumber(@PathVariable Long consumerNumber) {
        // if(session.getAttribute("isAdmin") == null || !(Boolean) session.getAttribute("isAdmin")) {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        // }
        return ResponseEntity.ok(adminService.getBillByConsumerNumber(consumerNumber));
    }

    @PostMapping("/add-bill")
    public ResponseEntity<Bill> addBillToConsumer(@RequestBody Bill bill) {
        // if(session.getAttribute("isAdmin") == null || !(Boolean) session.getAttribute("isAdmin")) {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        // }
        try{
            return ResponseEntity.ok(adminService.addBillToConsumer(bill));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
}

