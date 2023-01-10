package com.assignment.orderservice.Service.Implementation;

import com.assignment.orderservice.Dto.CustomerDto;
import com.assignment.orderservice.Model.Customer;
import com.assignment.orderservice.Repository.CustomerRepository;
import com.assignment.orderservice.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<CustomerDto> getCustomers() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        List<Customer> customers = customerRepository.findAll();
        for(Customer customer: customers){
            customerDtoList.add(convertEntityToDto(customer));
        }
        return customerDtoList;
    }

    private CustomerDto convertEntityToDto(Customer customer) {
        return CustomerDto.builder()
                            .name(customer.getName())
                            .email(customer.getEmail())
                            .build();
    }

    @Override
    public CustomerDto getCustomerById(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return convertEntityToDto(customer.get());
        } else {
            throw new EntityNotFoundException("Customer not found with ID: " + id);
        }
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .build();
        return customerRepository.save(customer);
    }

    @Override
    public CustomerDto updateCustomer(int id, CustomerDto updatedCustomer) {
        Optional<Customer> customerObj = customerRepository.findById(id);
        if (customerObj.isPresent()) {
            Customer customer = customerObj.get();
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            // update other attributes as needed
            return convertEntityToDto(customerRepository.save(customer));
        } else {
            throw new EntityNotFoundException("Customer not found with ID: " + id);
        }
    }

    @Override
    public CustomerDto deleteCustomer(int id) {
        Optional<Customer> customerObj = customerRepository.findById(id);
        if (customerObj.isPresent()) {
            Customer customer = customerObj.get();
            customerRepository.delete(customer);
            return convertEntityToDto(customer);
        } else {
            throw new EntityNotFoundException("Customer not found with ID: " + id);
        }
    }
}
