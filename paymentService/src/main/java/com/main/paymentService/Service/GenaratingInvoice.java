package com.main.paymentService.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.main.paymentService.Bean.Payment;
import com.main.paymentService.Repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class GenaratingInvoice {

    private final PaymentRepository paymentRepository;

    public GenaratingInvoice(PaymentRepository transactionRepository) {
        this.paymentRepository = transactionRepository;
    }

    public byte[] generateInvoiceById(String transactionId) throws IOException, DocumentException {
        // Fetch transaction from database
        Optional<Payment> optionalTransaction = paymentRepository.findByTransactionId(transactionId);

        if (optionalTransaction.isEmpty()) {
            throw new IllegalArgumentException("Transaction not found for ID: " + transactionId);
        }

        Payment transaction = optionalTransaction.get();

        // Generate Invoice Number
        String invoiceNumber = generateInvoiceNumber();

        // Create Document object
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // Add Invoice Details
        document.add(new Paragraph("Electricity Bill Invoice"));
        document.add(new Paragraph("Invoice Number: " + invoiceNumber));
        document.add(new Paragraph("Payment ID: " + transaction.getPaymentId()));
        document.add(new Paragraph("Transaction ID: " + transaction.getTransactionId()));
        document.add(new Paragraph("Amount: $" + transaction.getTransactionAmount()));
        document.add(new Paragraph("Transaction Date: " + transaction.getTransactionDate()));
        document.add(new Paragraph("Bill Number: " + transaction.getBillNumber()));
        document.add(new Paragraph("Transaction Type: " + transaction.getTransactionType()));
        document.add(new Paragraph("Transaction Status: " + transaction.getTransactionStatus()));

        document.close();
        return out.toByteArray();
    }

    private String generateInvoiceNumber() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int uniqueNumber = new Random().nextInt(900000) + 100000; // Generates a 6-digit number
        return "INV-" + date + "-" + uniqueNumber;
    }
}
