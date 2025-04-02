//package com.main.paymentService.Controller;
//
//import com.main.paymentService.Bean.Invoice;
//import com.main.paymentService.Service.InvoiceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/invoices")
//public class InvoiceController {
//
//    @Autowired
//    private InvoiceService invoiceService;
//
//    // Endpoint to create an invoice
//    @PostMapping("/create")
//    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
//        Invoice savedInvoice = invoiceService.createInvoice(invoice);
//        return ResponseEntity.ok(savedInvoice);
//    }
//
//    // Endpoint to get invoices by Payment ID
//    @GetMapping("/by-payment/{paymentId}")
//    public ResponseEntity<List<Invoice>> getInvoicesByPaymentId(@PathVariable String paymentId) {
//        List<Invoice> invoices = invoiceService.getInvoicesByPaymentId(paymentId);
//        return ResponseEntity.ok(invoices);
//    }
//}
