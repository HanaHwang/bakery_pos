package com.bakerypos.bakery_pos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.bakerypos.bakery_pos.repository.CustomerRepository;
import com.bakerypos.bakery_pos.model.Customer;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int id){
        return customerRepository.findById(id).orElse(null);
    }

    public void save(Customer customer){
        customerRepository.save(customer);
    }

    public void delete(int id){
        customerRepository.deleteById(id);
    }
}