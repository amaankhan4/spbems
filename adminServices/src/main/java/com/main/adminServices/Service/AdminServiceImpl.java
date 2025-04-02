package com.main.adminServices.Service;

import org.springframework.beans.factory.annotation.Autowired;
import com.main.adminServices.Bean.Consumer;
import com.main.adminServices.Dao.ConsumerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import com.main.adminServices.Bean.Admin;
import com.main.adminServices.Dao.AdminRepository;
import com.main.adminServices.Bean.Consumer.Status;
import com.main.adminServices.Utils.*;
import com.main.adminServices.Dto.Bill;




@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private ConsumerRepository consumerRepository;
    private AdminRepository adminRepository;
    private RestTemplate restTemplate;

    public AdminServiceImpl(ConsumerRepository consumerRepository, AdminRepository adminRepository,RestTemplate restTemplate){ 
        this.consumerRepository = consumerRepository;
        this.adminRepository = adminRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Consumer activateConsumer(String consumerNumber) throws IllegalArgumentException {
        Consumer consumer = consumerRepository.findByConsumerNumber(consumerNumber);
        if(consumer == null){
            throw new IllegalArgumentException("Consumer not found");
        }
        consumer.setStatus(Status.ACTIVE);
        return consumerRepository.save(consumer);
    }

    @Override
    public Consumer registerConsumer(Consumer consumer) throws IllegalArgumentException {
        Consumer existingConsumer = consumerRepository.findByConsumerNumber(consumer.getConsumerNumber());
        if(existingConsumer != null){
            throw new IllegalArgumentException("Consumer already exists");
        }
        return consumerRepository.save(consumer);
    }

    @Override
    public List<Consumer> getConsumerByCustomerId(Long customerId) {
        return consumerRepository.findByCustomerId(customerId);
    }

    @Override
    public Consumer updateConsumer(Consumer consumer) {
        return consumerRepository.save(consumer);
    }

    @Override
    public Consumer changeStatus(Consumer consumer, Status status) {
        consumer.setStatus(status);
        return consumerRepository.save(consumer);
    }

    @Override
    public Admin adminLogin(String email, String password) throws Exception {
        Admin admin = null;
        try{
            admin = adminRepository.findByEmail(email);
            if(admin != null && passwordUtils.matchPassword(password, admin.getPassword())){
                return admin;
            }
            else{
                throw new IllegalArgumentException("Invalid email or password");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Admin registerAdmin(Admin admin){
        admin.setPassword(passwordUtils.encryptPassword(admin.getPassword()));
        return adminRepository.save(admin);
    }

    @Override
    public Bill getBillByConsumerNumber(Long consumerNumber) throws IllegalArgumentException {
        String billUrl = "http://localhost:8081/bills/consumer/" + consumerNumber;
        return restTemplate.getForObject(billUrl, Bill.class);
    }

    @Override
    public Bill addBillToConsumer(Bill bill) throws IllegalArgumentException {
        String billUrl = "http://localhost:8081/bills/registerBill";
        return restTemplate.postForObject(billUrl, bill, Bill.class);
    }
}

