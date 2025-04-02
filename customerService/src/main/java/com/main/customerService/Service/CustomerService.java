package com.main.customerService.Service;


import com.main.customerService.Bean.Customer;


public interface CustomerService {

    Customer registerCustomer(Customer customer) throws Exception;

    Customer loginCustomer(String uniqueId, String password, String identifier) throws Exception;

    Customer getCustomerDetails(String userId);

    Customer updateCustomerDetails(String userId,Customer updatedCustomer) throws Exception;

}
