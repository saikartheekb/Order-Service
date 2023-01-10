package com.assignment.orderservice.Service;

import com.assignment.orderservice.Dto.CustomerDto;
import com.assignment.orderservice.Model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<CustomerDto> getCustomers();

    CustomerDto getCustomerById(int id);

    Customer createCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(int id, CustomerDto updatedCustomer);

    CustomerDto deleteCustomer(int id);
}
