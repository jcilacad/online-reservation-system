package com.system.reservation.online.controller;

import com.system.reservation.online.dto.ChangePasswordDto;
import com.system.reservation.online.dto.UserDto;
import com.system.reservation.online.enums.Role;
import com.system.reservation.online.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (userService.isUserAuthenticated(authentication)) {
            return "login";
        }

        return "redirect:/default";

    }

    @GetMapping("/users")
    public String users(Model model) {

        List<UserDto> userDtoList = userService.findAllDto();
        model.addAttribute("users", userDtoList);

        return "users";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {

        if (request.isUserInRole("ROLE_ADMIN")) {
            System.out.println("Admin Account");
            return "redirect:/admins/dashboard";
        } else {
            System.out.println("Student Account");
            return "redirect:/students/dashboard";
        }
    }

    @GetMapping("/account/password")
    public String getChangePassword(HttpServletRequest httpServletRequest,
                                    Model model) {

        // Initialize change password dto
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();

        model.addAttribute("changePasswordDto", changePasswordDto);

        if (httpServletRequest.isUserInRole(Role.ROLE_ADMIN.toString())) {
            return "admin/change-password";
        } else {
            return "student/change-password";
        }


    }

    @PostMapping("account/password")
    public String changePassword(Principal principal,
                                 HttpServletRequest httpServletRequest,
                                 @ModelAttribute(name = "changePasswordDto") ChangePasswordDto changePasswordDto,
                                 BindingResult result,
                                 Model model) {


        // field validation
        if (result.hasErrors()) {
            model.addAttribute("changePasswordDto", changePasswordDto);

            if (httpServletRequest.isUserInRole(Role.ROLE_ADMIN.toString())) {
                return "admin/change-password";
            } else {
                return "student/change-password";
            }

        }


        // Change password
        boolean isMatch = userService.changePassword(principal, changePasswordDto);

        // If there's an error, display error response
        if (!isMatch) {
            return "redirect:/account/password?error";
        }

        return "redirect:/account/password?success";


    }


}
