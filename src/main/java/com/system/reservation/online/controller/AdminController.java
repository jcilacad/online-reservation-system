package com.system.reservation.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @GetMapping("/dashboard")
    public String dashboard() {

        return "admin/dashboard";
    }
}
