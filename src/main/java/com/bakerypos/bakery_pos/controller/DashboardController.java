package com.bakerypos.bakery_pos.controller;

import com.bakerypos.bakery_pos.model.User;
import com.bakerypos.bakery_pos.service.BarangService;
import com.bakerypos.bakery_pos.service.CustomerService;
import com.bakerypos.bakery_pos.service.PenjualanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;

@Controller
public class DashboardController{
    private final PenjualanService penjualanService;
    private final CustomerService customerService;
    private final BarangService barangService;

    public DashboardController(PenjualanService penjualanService, CustomerService customerService, BarangService barangService){
        this.penjualanService = penjualanService;
        this.customerService = customerService;
        this.barangService = barangService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model){
        User user = (User) session.getAttribute("loggedInUser");
        if(user == null){
            return "redirect:/auth/login";
        }

        model.addAttribute("user", user);

        if(user.isAdmin()){
            var salesList = penjualanService.getAllPenjualan();
            int totalSalesToday = 0;
            int countSalesToday = 0;
            LocalDateTime startOfToday = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

            for(var sale:salesList){
                if(sale.getTanggal() != null && sale.getTanggal().isAfter(startOfToday)){
                    totalSalesToday+=sale.getTotalBayar();
                    countSalesToday++;
                }
            }

            long lowStokCount = barangService.getAllBarang().stream().filter(b->b.getStok()<5).count();
            model.addAttribute("totalSalesToday", totalSalesToday);
            model.addAttribute("countSalesToday", countSalesToday);
            model.addAttribute("totalCustomer", customerService.getAllCustomer().size());
            model.addAttribute("lowStokCount", (int) lowStokCount);
        }
        return "dashboard";
    }
}