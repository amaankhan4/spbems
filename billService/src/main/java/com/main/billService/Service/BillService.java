package com.main.billService.Service;

//import com.main.billService.*;
import com.main.billService.Bean.Bill;
import com.main.billService.DAO.BillRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    
    @Autowired
    private BillRepository billRepository;

    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    public Optional<Bill> getBillByBillNumber(Long billNumber) {
        return billRepository.findByBillNumber(billNumber);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> getBillsByConsumerNumber(String consumerNumber) {
        return billRepository.findByConsumerNumber(consumerNumber);
    }

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

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }
}
