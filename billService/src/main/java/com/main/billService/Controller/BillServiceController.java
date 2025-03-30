package com.main.billService.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.main.billService.Bean.Bill;
import com.main.billService.Service.BillService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bills")
public class BillServiceController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        return ResponseEntity.ok(billService.createBill(bill));
    }

    @GetMapping("/billNumber/{billNumber}")
    public ResponseEntity<Bill> getBillByBillNumber(@PathVariable Long billNumber) {
        Optional<Bill> bill = billService.getBillByBillNumber(billNumber);
        return bill.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @GetMapping("/consumer/{consumerNumber}")
    public ResponseEntity<List<Bill>> getBillsByConsumerNumber(@PathVariable String consumerNumber) {
        return ResponseEntity.ok(billService.getBillsByConsumerNumber(consumerNumber));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable Long id, @RequestBody Bill updatedBill) {
        Bill bill = billService.updateBill(id, updatedBill);
        return bill != null ? ResponseEntity.ok(bill) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping("/")
    public String HealthCheck(){
        return "Health Check Passed.";
    }
}