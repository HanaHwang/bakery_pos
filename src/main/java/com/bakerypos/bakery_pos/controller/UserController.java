package com.bakerypos.bakery_pos.controller;

import com.bakerypos.bakery_pos.model.User;
import com.bakerypos.bakery_pos.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController{
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    private boolean isNotAdmin(HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        return user == null || !user.isAdmin();
    }

    @GetMapping
    public String index(Model model, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        model.addAttribute("listUser", userService.getAllUser());
        return "user/index";
    }

    @GetMapping("/tambah")
    public String tambah(Model model, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        model.addAttribute("userForm", new User());
        return "user/form";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute("userForm") User user, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        userService.save(user);
        return "redirect:/user";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        User user = userService.getUserById(id);
        if(user == null){
            return "redirect:/user";
        }
        model.addAttribute("userForm", user);
        return "user/form";
    }

    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable int id, HttpSession session){
        if(isNotAdmin(session)) return "redirect:/dashboard";
        userService.delete(id);
        return "redirect:/user";
    }
}
