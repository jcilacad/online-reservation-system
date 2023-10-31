package com.system.reservation.online.controller;

import com.system.reservation.online.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @GetMapping({"/dashboard", "/", ""})
    public String dashboard() {

        return "admin/dashboard";
    }

    @GetMapping("/accounts")
    public String accounts(Model model) {

        // Instantiate userDto for form
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        
        return "admin/account";
    }
}
