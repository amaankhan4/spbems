package com.main.billService.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.main.billService.Bean.Bill;
import com.main.billService.Service.BillService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/bills")
@CrossOrigin("*")
public class BillServiceController {

    @Autowired
    private BillService billService;

    @PostMapping("/registerBill")
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        return ResponseEntity.ok(billService.createBill(bill));
    }

    @GetMapping("/billNumber/{billNumber}")
    public ResponseEntity<Bill> getBillByBillNumber(@PathVariable Long billNumber) {
        Optional<Bill> bill = billService.getBillByBillNumber(billNumber);
        return bill.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/allBills")
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @GetMapping("/consumer/{consumerNumber}")
    public ResponseEntity<List<Bill>> getBillsByConsumerNumber(@PathVariable String consumerNumber) {
        return ResponseEntity.ok(billService.getBillsByConsumerNumber(consumerNumber));
    }

    @PutMapping("/updateBill/{billNumber}")
    public ResponseEntity<Bill> updateBill(@PathVariable Long billNumber, @RequestBody Bill updatedBill) {
        Bill bill = billService.updateBill(billNumber, updatedBill);
        return bill != null ? ResponseEntity.ok(bill) : ResponseEntity.notFound().build();
    }

//    @DeleteMapping("/deleteBill/{billNumber}")
//    public ResponseEntity<Void> deleteBill(@PathVariable Long billNumber) {
//        billService.deleteBill(billNumber);
//        return ResponseEntity.noContent().build();
//    }
    
    @PutMapping("/update-payment-status")
    public ResponseEntity<String> updatePaymentStatus(@RequestBody Map<String, Object> request) {
        Long billNumber = ((Number) request.get("billNumber")).longValue();
        Float transactionAmount = ((Number) request.get("transactionAmount")).floatValue();

        billService.updatePaymentStatus(billNumber, transactionAmount);

        return ResponseEntity.ok("Bill payment status updated successfully.");
    }

    @RequestMapping("/")
    public String HealthCheck(){
        return "Health Check Passed.";
    }
}



