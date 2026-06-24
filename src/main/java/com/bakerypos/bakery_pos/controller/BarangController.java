package com.bakerypos.bakery_pos.controller;

import com.bakerypos.bakery_pos.model.Barang;
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

    private boolean belumLogin(HttpSession session){
        return session.getAttribute("loggedInUser")==null;
    }

    @GetMapping
    public String index(Model model, HttpSession session){
        if(belumLogin(session)){
            return "redirect:/";
        }
        model.addAttribute("listBarang", barangService.getAllBarang());
        return "barang/index";
    }

    @GetMapping("/tambah")
    public String tambah(Model model){
        model.addAttribute("barang", new Barang());
        model.addAttribute("mode", "tambah");
        return "barang/form";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Barang barang){
        barangService.save(barang);
        return "redirect:/barang";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        Barang barang = barangService.getBarangById(id);
        model.addAttribute("barang", barang);
        model.addAttribute("mode", "edit");
        return "barang/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Barang barang){
        barangService.save(barang);
        return "redirect:/barang";
    }

    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable int id){
        barangService.delete(id);
        return "redirect:/barang";
    }
}