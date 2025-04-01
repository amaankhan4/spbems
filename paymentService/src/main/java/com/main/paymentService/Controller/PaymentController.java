package com.main.paymentService.Controller;

import com.main.paymentService.Bean.Invoice;
import com.main.paymentService.Bean.Payment;
import com.main.paymentService.Service.InvoiceService;
import com.main.paymentService.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/transactions")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/pay")
    public ResponseEntity<Payment> makePayment(@RequestBody Payment transaction) {
        Payment savedTransaction = paymentService.createPayment(transaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Payment> getTransaction(@PathVariable String transactionId) {
        return paymentService.getTransactionById(transactionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Generate and return invoice after successful payment
    @PostMapping("/generate-invoice/{transactionId}")
    public ResponseEntity<Invoice> generateInvoice(@PathVariable String transactionId) {
        try {
            Invoice invoice = invoiceService.generateInvoice(transactionId);
            return ResponseEntity.ok(invoice);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    

    // Download invoice as PDF
    @GetMapping("/download/{transactionId}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable String transactionId) {
        try {
            byte[] invoicePdf = invoiceService.generateInvoicePdf(transactionId);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + transactionId + ".pdf")
                    .body(invoicePdf);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping("/")
    public String healthCheck() {
        return "Health check passed";
    }
}










//package com.main.paymentService.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
////import org.springframework.web.bind.annotation.*;
//
//
//
//
//
//
//import com.main.paymentService.Bean.Payment;
//import com.main.paymentService.Service.GenaratingInvoice;
//import com.main.paymentService.Service.PaymentService;
//
//
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/api/transactions")
//public class PaymentController {
//
//    @Autowired
//    private PaymentService paymentService;
//    private final GenaratingInvoice GenaratingInvoice;
//
//    
//    
//    public PaymentController(GenaratingInvoice invoiceService) {
//        this.GenaratingInvoice = invoiceService;
//    }
//
//    @PostMapping("/pay")
//    public ResponseEntity<Payment> makePayment(@RequestBody Payment transaction) {
//        Payment savedTransaction = paymentService.createPayment(transaction);
//        return ResponseEntity.ok(savedTransaction);
//    }
//
//    @GetMapping("/{transactionId}")
//    public ResponseEntity<Payment> getTransaction(@PathVariable String transactionId) {
//        return paymentService.getTransactionById(transactionId)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//    
//    
//    @GetMapping("/download/{transactionId}")
//    public ResponseEntity<byte[]> downloadInvoice(@PathVariable String transactionId) {
//        try {
//            byte[] invoicePdf = GenaratingInvoice.generateInvoiceById(transactionId);
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + transactionId + ".pdf")
//                    .body(invoicePdf);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//    
//    @RequestMapping("/")
//    public String HealthCheck() {
//    	return "Health check passsed";
//    }
//    
//    
//}

