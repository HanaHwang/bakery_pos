package com.bakerypos.bakery_pos.controller;

import com.bakerypos.bakery_pos.model.Barang;
import com.bakerypos.bakery_pos.model.User;
import com.bakerypos.bakery_pos.service.BarangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/barang")
public class BarangController{
    private final BarangService barangService;
    public BarangController(BarangService barangService){
        this.barangService = barangService;
    }

    private boolean isNotAdmin(HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        return user == null || !user.isAdmin();
    }

    private boolean belumLogin(HttpSession session){
        return session.getAttribute("loggedInUser")==null;
    }

    @GetMapping
    public String index(Model model, HttpSession session){
        if(belumLogin(session)) return "redirect:/auth/login";
        model.addAttribute("listBarang", barangService.getAllBarang());
        return "barang/index";
    }

    @GetMapping("/tambah")
    public String tambah(Model model, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        model.addAttribute("barang", new Barang());
        return "barang/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        Barang barang = barangService.getBarangById(id);
        model.addAttribute("barang", barang);
        return "barang/form";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Barang barang, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        barangService.save(barang);
        return "redirect:/barang";
    }

    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable int id, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        barangService.delete(id);
        return "redirect:/barang";
    }
}