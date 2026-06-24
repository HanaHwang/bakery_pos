package com.bakerypos.bakery_pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.bakerypos.bakery_pos.model.Customer;
import com.bakerypos.bakery_pos.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController{
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("listCustomer", customerService.getAllCustomer());
        return "customer/index";
    }

    @GetMapping("/tambah")
    public String tambah(Model model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("mode", "tambah");
        return "customer/form";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Customer customer){
        customerService.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("mode", "edit");
        return "customer/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Customer customer){
        customerService.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable int id){
        customerService.delete(id);
        return "redirect:/customer";
    }
}