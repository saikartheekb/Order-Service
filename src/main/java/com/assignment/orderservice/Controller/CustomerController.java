package com.assignment.orderservice.Controller;

import com.assignment.orderservice.Dto.CustomerDto;
import com.assignment.orderservice.Model.Customer;
import com.assignment.orderservice.Model.Exceptions.CustomerEntityException;
import com.assignment.orderservice.Service.Implementation.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;

    @PostMapping()
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDto customerDto) throws CustomerEntityException {
        try{
            Customer customer = customerService.createCustomer(customerDto);
            return new ResponseEntity<>(customer.getName() + "'s account is created with id " + customer.getId(), HttpStatus.CREATED);
        } catch (Exception e){
            throw new CustomerEntityException(e);
        }
    }

    @GetMapping()
    public ResponseEntity<List<CustomerDto>> getCustomers() throws CustomerEntityException{
        try{
            return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.FOUND);
        } catch (Exception e){
            throw new CustomerEntityException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable int id) throws CustomerEntityException {
        try{
            CustomerDto customer = customerService.getCustomerById(id);
            return new ResponseEntity<>(customer, HttpStatus.FOUND);
        } catch (Exception e){
            throw new CustomerEntityException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable int id, @RequestBody CustomerDto updatedCustomer) throws CustomerEntityException {
        try{
            return new ResponseEntity<>(customerService.updateCustomer(id, updatedCustomer), HttpStatus.ACCEPTED);
        } catch (Exception e){
            throw new CustomerEntityException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) throws CustomerEntityException {
        try{
            CustomerDto customer = customerService.deleteCustomer(id);
            return new ResponseEntity<>(customer.getName() + " is deleted", HttpStatus.ACCEPTED);
        } catch (Exception e){
            throw new CustomerEntityException(e);
        }
    }

}
