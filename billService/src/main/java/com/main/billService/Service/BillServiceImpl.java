package com.main.billService.Service;

//import com.main.billService.*;
import com.main.billService.Bean.Bill;
import com.main.billService.Dao.BillRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService{
    
    @Autowired
    private BillRepository billRepository;
    
    @Override
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }
    
    @Override
    public Optional<Bill> getBillByBillNumber(Long billNumber) {
        return billRepository.findByBillNumber(billNumber);
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Override
    public List<Bill> getBillsByConsumerNumber(String consumerNumber) {
        return billRepository.findByConsumerNumber(consumerNumber);
    }
    
    @Override
    public Bill updateBill(Long id, Bill updatedBill) {
        return billRepository.findById(id).map(bill -> {
            bill.setPaymentStatus(updatedBill.getPaymentStatus());
            bill.setConnectionType(updatedBill.getConnectionType());
            bill.setConnectionStatus(updatedBill.getConnectionStatus());
            bill.setBillPeriod(updatedBill.getBillPeriod());
            bill.setBillDate(updatedBill.getBillDate());
            bill.setDueDate(updatedBill.getDueDate());
            bill.setDisconnectionDate(updatedBill.getDisconnectionDate());
            bill.setDueAmount(updatedBill.getDueAmount());
            bill.setPayableAmount(updatedBill.getPayableAmount());
            return billRepository.save(bill);
        }).orElse(null);
    }
    
    public void updatePaymentStatus(Long billNumber, Float transactionAmount) {
        Bill bill = billRepository.findByBillNumber(billNumber)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));

        // Subtract transaction amount from the due amount
        Float updatedDueAmount = Math.max(0, bill.getDueAmount() - transactionAmount);
        bill.setDueAmount(updatedDueAmount);

        // If the due amount is now zero, mark the bill as PAID
        if (updatedDueAmount == 0) {
            bill.setPaymentStatus(Bill.PaymentStatus.PAID);
        }

        // Save updated bill in database
        billRepository.save(bill);
    }

//    public void deleteBill(Long id) {
//        billRepository.deleteById(id);
//    }
}




