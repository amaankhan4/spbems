package com.main.adminServices.Service;

import org.springframework.stereotype.Service;
import com.main.adminServices.Bean.Consumer;
import java.util.List;
import com.main.adminServices.Bean.Consumer.Status;
import com.main.adminServices.Dto.Bill;
import com.main.adminServices.Bean.Admin;


@Service
public interface AdminService {

    Consumer activateConsumer(String consumerNumber) throws IllegalArgumentException;

    Consumer registerConsumer(Consumer consumer) throws IllegalArgumentException;

    List<Consumer> getConsumerByCustomerId(Long customerId);

    Consumer updateConsumer(Consumer consumer);

    Consumer changeStatus(Consumer consumer, Status status);

    Admin adminLogin(String email, String password) throws Exception;

    Admin registerAdmin(Admin admin);

    Bill getBillByConsumerNumber(Long consumerNumber) throws IllegalArgumentException;

    Bill addBillToConsumer(Bill bill) throws IllegalArgumentException;

}

