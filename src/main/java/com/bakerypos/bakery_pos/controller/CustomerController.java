package com.bakerypos.bakery_pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.bakerypos.bakery_pos.model.Customer;
import com.bakerypos.bakery_pos.model.User;
import com.bakerypos.bakery_pos.service.CustomerService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class CustomerController{
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    private boolean belumLogin(HttpSession session){
        return session.getAttribute("loggedInUser") == null;
    }

    private boolean isNotAdmin(HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        return user == null || !user.isAdmin();
    }

    @GetMapping
    public String index(Model model, HttpSession session){
        if(belumLogin(session)) return "redirect:/auth/login";
        model.addAttribute("listCustomer", customerService.getAllCustomer());
        return "customer/index";
    }

    @GetMapping("/tambah")
    public String tambah(Model model, HttpSession session){
        if(belumLogin(session)) return "redirect:/auth/login";
        model.addAttribute("customer", new Customer());
        return "customer/form";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Customer customer, HttpSession session){
        if(belumLogin(session)) return "redirect:/auth/login";
        customerService.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customer/form";
    }

    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable int id, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        customerService.delete(id);
        return "redirect:/customer";
    }
}