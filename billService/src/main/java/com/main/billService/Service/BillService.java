package com.main.billService.Service;

import java.util.*;
import com.main.billService.Bean.Bill;

public interface BillService {

	Bill createBill(Bill bill);

	Optional<Bill> getBillByBillNumber(Long billNumber);

	List<Bill> getAllBills();

	List<Bill> getBillsByConsumerNumber(String consumerNumber);
	
	Bill updateBill(Long id, Bill updatedBill);
	
	void updatePaymentStatus(Long billNumber, Float transactionAmount);

}

