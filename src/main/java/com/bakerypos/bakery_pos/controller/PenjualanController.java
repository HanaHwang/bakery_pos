package com.bakerypos.bakery_pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bakerypos.bakery_pos.service.BarangService;
import com.bakerypos.bakery_pos.service.CustomerService;

@Controller
@RequestMapping("/penjualan")
public class PenjualanController{
    private final BarangService barangService;
    private final CustomerService customerService;

    public PenjualanController(BarangService barangService, CustomerService customerService){
        this.barangService = barangService;
        this.customerService = customerService;
    }

    @GetMapping
    public String formPenjualan(Model model){
        model.addAttribute("listBarang", barangService.getAllBarang());
        model.addAttribute("listCustomer", customerService.getAllCustomer());
        return "penjualan/form";
    }
}