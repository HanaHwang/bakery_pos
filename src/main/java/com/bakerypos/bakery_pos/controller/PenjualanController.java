package com.bakerypos.bakery_pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.bakerypos.bakery_pos.model.Penjualan;
import com.bakerypos.bakery_pos.model.User;
import com.bakerypos.bakery_pos.service.BarangService;
import com.bakerypos.bakery_pos.service.CustomerService;
import com.bakerypos.bakery_pos.service.PenjualanService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/penjualan")
public class PenjualanController{
    private final BarangService barangService;
    private final CustomerService customerService;
    private final PenjualanService penjualanService;

    public PenjualanController(BarangService barangService, CustomerService customerService, PenjualanService penjualanService){
        this.barangService = barangService;
        this.customerService = customerService;
        this.penjualanService = penjualanService;
    }

    private boolean belumLogin(HttpSession session){
        return session.getAttribute("loggedInUser") == null;
    }

    private boolean isNotAdmin(HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        return user == null || !user.isAdmin();
    }

    @GetMapping
    public String formPenjualan(Model model, HttpSession session){
        if (belumLogin(session)) return "redirect:/auth/login";
        Penjualan penjualan = new Penjualan();
        penjualan.setTanggal(LocalDateTime.now());
        model.addAttribute("penjualan", penjualan);
        model.addAttribute("listBarang", barangService.getAllBarang());
        model.addAttribute("listCustomer", customerService.getAllCustomer());
        return "penjualan/form";
    }

    @GetMapping("/history")
    public String historyPenjualan(Model model, HttpSession session){
        if (belumLogin(session)) return "redirect:/auth/login";
        model.addAttribute("listPenjualan", penjualanService.getAllPenjualan());
        return "penjualan/history";
    }

    @GetMapping("/detail/{id}")
    public String detailPenjualan(@PathVariable int id, Model model, HttpSession session){
        if (belumLogin(session)) return "redirect:/auth/login";
        Penjualan penjualan = penjualanService.getPenjualanById(id);
        if (penjualan == null){
            return "redirect:/penjualan/history";
        }
        model.addAttribute("penjualan", penjualan);
        return "penjualan/detail";
    }

    @PostMapping("/save")
    public String simpanPenjualan(@ModelAttribute("penjualan") Penjualan penjualan, HttpSession session, RedirectAttributes redirectAttributes) {
        if (belumLogin(session)) return "redirect:/auth/login";
        try{
            User currentStaff = (User) session.getAttribute("loggedInUser");
            penjualan.setUser(currentStaff);
            penjualan.setTanggal(LocalDateTime.now());
            Penjualan savedSale = penjualanService.save(penjualan);
            redirectAttributes.addFlashAttribute("successMessage", "Transaksi berhasil disimpan!");
            return "redirect:/penjualan/detail/" + savedSale.getIdPenjualan();
        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/penjualan";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePenjualan(@PathVariable int id, HttpSession session) {
        if (isNotAdmin(session)) return "redirect:/dashboard";
        penjualanService.delete(id);
        return "redirect:/penjualan/history";
    }
}