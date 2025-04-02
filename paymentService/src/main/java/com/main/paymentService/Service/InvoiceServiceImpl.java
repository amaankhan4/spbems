package com.main.paymentService.Service;

import com.main.paymentService.Bean.Invoice;
import com.main.paymentService.Dao.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;

    // Create Invoice
    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    // Get Invoices by Payment ID
    public List<Invoice> getInvoicesByPaymentId(String paymentId) {
        return invoiceRepository.findByPaymentId(paymentId);
    }
}
