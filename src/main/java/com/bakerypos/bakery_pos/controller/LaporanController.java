package com.bakerypos.bakery_pos.controller;

import com.bakerypos.bakery_pos.model.User;
import com.bakerypos.bakery_pos.model.Penjualan;
import com.bakerypos.bakery_pos.service.PenjualanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/laporan")
public class LaporanController{
    private final PenjualanService penjualanService;

    public LaporanController(PenjualanService penjualanService){
        this.penjualanService = penjualanService;
    }

    private boolean isNotAdmin(HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        return user == null || !user.isAdmin();
    }

    @GetMapping
    public String index(HttpSession session, Model model){
        if (isNotAdmin(session)){
            return "redirect:/dashboard";
        }
        List<Penjualan> allSales = penjualanService.getAllPenjualan();
        
        int omsetTotal = 0;
        for (Penjualan p : allSales){
            omsetTotal += p.getTotalBayar();
        }
        model.addAttribute("omsetTotal", omsetTotal);
        model.addAttribute("jumlahTransaksi", allSales.size());
        model.addAttribute("filteredSalesList", allSales);
        model.addAttribute("topProdukList", List.of()); 

        return "laporan/index";
    }
}
