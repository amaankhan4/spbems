package com.main.customerService.Service;

import com.main.customerService.Bean.Customer;
import com.main.customerService.Dao.CustomerRepository;
import com.main.customerService.Exceptions.InvalidCredentials;

import org.springframework.stereotype.Service;
import com.main.customerService.Utils.passwordUtils;

@Service
public class CustomerServiceImpl implements CustomerService {
    public final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer registerCustomer(Customer customer) throws InvalidCredentials {

        if(customer == null){
            throw new InvalidCredentials("Customer details cannot be null");
        }

        customer.setPassword(passwordUtils.encryptPassword(customer.getPassword()));

        if (customerRepository.findByUserId(customer.getUserId()) != null) {
            throw new InvalidCredentials("User ID already exists. Please choose a different User ID.");
        }   
        return customerRepository.save(customer);
    }

    @Override
    public Customer loginCustomer(String uniqueId, String password, String identifier) throws InvalidCredentials {

        Customer customer = null;

        if(identifier.charAt(0) == 'u'){
        customer = customerRepository.findByUserId(uniqueId);
        }else if(identifier.charAt(0) == 'e'){
            customer = customerRepository.findByEmail(uniqueId);
        }

        if (customer == null) {
            System.out.println("User ID not found");
            throw new InvalidCredentials("Invalid User ID or Password.");
        }

        boolean checkPassword = passwordUtils.matchPassword(password, customer.getPassword());
        if (!checkPassword) {
            System.out.println("Password does not match");
            throw new InvalidCredentials("Passwords do not match.");
        }

        System.out.println(customer);
        return customer;
    }

    @Override
    public Customer getCustomerDetails(String userId){
        Customer customer = customerRepository.findByUserId(userId);
        if (customer == null) {
            return null;
        }
        return customer;
    }

    @Override
    public Customer updateCustomerDetails(String userId,Customer updatedCustomer) throws Exception {
        if (updatedCustomer == null) {
            throw new Exception("Updated customer details cannot be null");
        }
        Customer existingCustomer = customerRepository.findByUserId(userId);
        if (existingCustomer == null) {
            throw new Exception("Customer not found with ID: " + userId);
        }
            
            if (updatedCustomer.getFullName() != null) {
                existingCustomer.setFullName(updatedCustomer.getFullName());
            }
            if (updatedCustomer.getEmail() != null) {
                existingCustomer.setEmail(updatedCustomer.getEmail());
            }
            if (updatedCustomer.getMobileNumber() != null) {
                existingCustomer.setMobileNumber(updatedCustomer.getMobileNumber());
            }
            if (updatedCustomer.getUserId() != null) {
                existingCustomer.setUserId(updatedCustomer.getUserId());
            }
            if (updatedCustomer.getPassword() != null) {
                String encryptedPassword = passwordUtils.encryptPassword(updatedCustomer.getPassword());
                existingCustomer.setPassword(encryptedPassword);
            }

            return customerRepository.save(existingCustomer);
    }
}
