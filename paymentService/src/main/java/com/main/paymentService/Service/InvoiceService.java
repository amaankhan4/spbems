package com.main.paymentService.Service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.main.paymentService.Bean.Invoice;
import com.main.paymentService.Bean.Payment;
import com.main.paymentService.Repository.InvoiceRepository;
import com.main.paymentService.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Invoice generateInvoice(String transactionId) {
        Optional<Payment> paymentOpt = paymentRepository.findByTransactionId(transactionId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();

            if (!payment.getTransactionStatus().equals(Payment.TransactionStatus.SUCCESS)) {
                throw new IllegalStateException("Invoice can only be generated for successful payments.");
            }

            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 10));
            invoice.setPaymentId(payment.getPaymentId());
            invoice.setTransactionId(payment.getTransactionId());
            invoice.setReceiptNumber(payment.getReceiptNumber());
            invoice.setTransactionDate(payment.getTransactionDate());
            invoice.setTransactionType(payment.getTransactionType());
            invoice.setBillNumber(payment.getBillNumber());
            invoice.setTransactionAmount(payment.getTransactionAmount());
            invoice.setTransactionStatus(payment.getTransactionStatus());

            Invoice savedInvoice = invoiceRepository.save(invoice);

            // Generate PDF for the invoice after saving it
            try {
                byte[] pdfBytes = generateInvoicePdf(savedInvoice.getTransactionId());
                // You can store the PDF or send it as needed.
            } catch (DocumentException | IOException e) {
                throw new RuntimeException("Error generating invoice PDF", e);
            }

            return savedInvoice;
        } else {
            throw new IllegalArgumentException("Transaction ID not found.");
        }
    }

    // Generate PDF for the invoice
    public byte[] generateInvoicePdf(String transactionId) throws IOException, DocumentException {
        Optional<Invoice> invoiceOpt = invoiceRepository.findByTransactionId(transactionId);
        if (!invoiceOpt.isPresent()) {
            throw new IllegalArgumentException("Invoice not found for transaction ID: " + transactionId);
        }
        Invoice invoice = invoiceOpt.get();

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // Add invoice header
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        document.add(new Paragraph("Invoice", titleFont));
        document.add(new Paragraph("\nInvoice Details:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));

        // Create table to display invoice details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        table.addCell(new PdfPCell(new Paragraph("Invoice Number:")));
        table.addCell(new PdfPCell(new Paragraph(invoice.getInvoiceNumber())));

        table.addCell(new PdfPCell(new Paragraph("Payment ID:")));
        table.addCell(new PdfPCell(new Paragraph(invoice.getPaymentId())));

        table.addCell(new PdfPCell(new Paragraph("Transaction ID:")));
        table.addCell(new PdfPCell(new Paragraph(invoice.getTransactionId())));

        table.addCell(new PdfPCell(new Paragraph("Transaction Date:")));
        table.addCell(new PdfPCell(new Paragraph(invoice.getTransactionDate().toString())));

        table.addCell(new PdfPCell(new Paragraph("Transaction Amount:")));
        table.addCell(new PdfPCell(new Paragraph(invoice.getTransactionAmount().toString())));

        table.addCell(new PdfPCell(new Paragraph("Transaction Type:")));
        table.addCell(new PdfPCell(new Paragraph(invoice.getTransactionType().toString())));

        table.addCell(new PdfPCell(new Paragraph("Bill Number:")));
        table.addCell(new PdfPCell(new Paragraph(invoice.getBillNumber().toString())));

        table.addCell(new PdfPCell(new Paragraph("Transaction Status:")));
        table.addCell(new PdfPCell(new Paragraph(invoice.getTransactionStatus().toString())));

        // Add table to the document
        document.add(table);
        document.close();

        return out.toByteArray(); // Return the PDF as byte array
    }
}
