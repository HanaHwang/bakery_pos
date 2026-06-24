package com.bakerypos.bakery_pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bakerypos.bakery_pos.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
}