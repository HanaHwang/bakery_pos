package com.bakerypos.bakery_pos.controller;

import com.bakerypos.bakery_pos.model.User;
import com.bakerypos.bakery_pos.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController{
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/")
    public String rootPage(){
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String noTelp, @RequestParam String password, HttpSession session, Model model){
        User user = authService.login(noTelp, password);
        if(user != null){
            session.setAttribute("loggedInUser", user);
            session.setAttribute("userNama", user.getNamaUser());
            session.setAttribute("userRole", user.getRole().toLowerCase());
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "No telepon atau password salah");
        return "login";
    }

    @GetMapping("/auth/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/auth/login";
    }
}