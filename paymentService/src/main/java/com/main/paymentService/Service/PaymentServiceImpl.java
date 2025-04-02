//package com.main.paymentService.Service;
//
//import com.main.paymentService.Bean.Invoice;
//import com.main.paymentService.Bean.Payment;
//import com.main.paymentService.DTO.BillDTO;
//import com.main.paymentService.Dao.PaymentRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.sql.Timestamp;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//@Service
//public class PaymentServiceImpl implements PaymentService {
//
//    @Autowired
//    private PaymentRepository paymentRepository;
//    
//
//    @Autowired
//    private RestTemplate restTemplate;  // To fetch bill details from Bill Service
//
//    private static final String BILL_SERVICE_URL = "http://172.20.10.5:8081/bills/billNumber/";
//
//    @Override
//    public Payment createPayment(Payment payment) {
//        // Check if the bill exists in Bill Service
//        BillDTO billDTO = restTemplate.getForObject(BILL_SERVICE_URL + payment.getBillNumber(), BillDTO.class);
//
//        if (billDTO == null) {
//            throw new IllegalArgumentException("Bill with number " + payment.getBillNumber() + " does not exist.");
//        }
//        updateBillPaymentStatus(payment.getBillNumber(), payment.getTransactionAmount());
//        // Save payment if the bill exists
//        return paymentRepository.save(payment);
//    }
//    
//    private void updateBillPaymentStatus(Long billNumber, Float transactionAmount) {
//        String updateUrl = "http://172.20.10.5:8081/bills/update-payment-status";
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("billNumber", billNumber);
//        request.put("transactionAmount", transactionAmount);
//
//        restTemplate.put(updateUrl, request);
//    }
//    
//}

package com.main.paymentService.Service;

import com.main.paymentService.Bean.Invoice;
import com.main.paymentService.Bean.Payment;
import com.main.paymentService.DTO.BillDTO;
import com.main.paymentService.Dao.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;  // To fetch bill details from Bill Service

    @Autowired
    private InvoiceService invoiceService;  // Invoice Service Dependency

    private static final String BILL_SERVICE_URL = "http://172.20.10.5:8081/bills/billNumber/";

    @Override
    public Payment createPayment(Payment payment) {
        // Check if the bill exists in Bill Service
        BillDTO billDTO = restTemplate.getForObject(BILL_SERVICE_URL + payment.getBillNumber(), BillDTO.class);

        if (billDTO == null) {
            throw new IllegalArgumentException("Bill with number " + payment.getBillNumber() + " does not exist.");
        }

        // Save payment
        Payment savedPayment = paymentRepository.save(payment);

        // Update Bill Service payment status
        updateBillPaymentStatus(payment.getBillNumber(), payment.getTransactionAmount());

        // Generate Invoice
        //createInvoiceForPayment(savedPayment, billDTO);

        return savedPayment;
    }

    private void updateBillPaymentStatus(Long billNumber, Float transactionAmount) {
        String updateUrl = "http://172.20.10.5:8081/bills/update-payment-status";

        Map<String, Object> request = new HashMap<>();
        request.put("billNumber", billNumber);
        request.put("transactionAmount", transactionAmount);

        restTemplate.put(updateUrl, request);
    }

//    private void createInvoiceForPayment(Payment payment, BillDTO billDTO) {
//    	Invoice invoice = new Invoice();
//    	invoice.setPaymentId(payment.getPaymentId());
//    	invoice.setTransactionId(payment.getTransactionId());
//    	invoice.setReceiptNumber(payment.getReceiptNumber());
//    	invoice.setConsumerNumber(payment.getConsumerNumber()); // Fetch from Bill Service
//    	invoice.setTransactionDate(LocalDateTime.now());
//    	invoice.setTransactionType(Invoice.TransactionType.CREDIT);
//    	invoice.setBillNumber(payment.getBillNumber().toString());
//    	invoice.setTransactionAmount(payment.getTransactionAmount().doubleValue());
//    	invoice.setTransactionStatus(Invoice.TransactionStatus.SUCCESS);
//
//        // Save Invoice in InvoiceService
//        invoiceService.createInvoice(invoice);
//    }
}

