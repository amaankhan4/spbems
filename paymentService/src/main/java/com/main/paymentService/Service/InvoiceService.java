package com.main.paymentService.Service;

import java.util.List;

import com.main.paymentService.Bean.Invoice;

public interface InvoiceService {
	
	Invoice createInvoice(Invoice invoice);
	
	List<Invoice> getInvoicesByPaymentId(String paymentId);

}
