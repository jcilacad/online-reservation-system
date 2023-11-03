package com.system.reservation.online.controller;

import com.system.reservation.online.dto.UserDto;
import com.system.reservation.online.entity.User;
import com.system.reservation.online.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {

    UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/dashboard", "/", ""})
    public String dashboard() {

        return "admin/dashboard";
    }

    @GetMapping("/accounts")
    public String accounts(Model model,
                           @RequestParam(defaultValue = "0") Integer page) {


        // Instantiate userDto for form
        UserDto user = new UserDto();

        // Get the list of users
//        List<User> users = userService.findAll();
        Page<User> users = userService.findAllPaginated(page, 10);

        model.addAttribute("users", users);
        model.addAttribute("userDto", user);
        model.addAttribute("page", page);

        return "admin/account";
    }

    @PostMapping("/accounts")
    public String addAccount(@Valid @ModelAttribute("userDto") UserDto userDto,
                                                               BindingResult result,
                                                               Model model) {

        // Check if user exists
        boolean isUserExists = userService.isUserExists(userDto);

        if (isUserExists) {
            result.rejectValue("email", null, "Email already exists!");
        }

        // Error handler if there's empty field
        if (result.hasErrors()) {
            System.out.println("May error");
            model.addAttribute("userDto", userDto);
            return "admin/account";
        }

        // Save user
        userService.saveUser(userDto);

        return "redirect:/admins/accounts?success";
    }


    @GetMapping("/accounts/{studentId}")
    public String viewStudentDetails(@PathVariable Long studentId,
                                     Model model) {

        // Find student by id
        User student = userService.findByStudentId(studentId);

        // Map user object to Dto
        UserDto studentDto = userService.mapUserToDto(student);

        model.addAttribute("student", student);
        model.addAttribute("studentDto", studentDto);

        return "admin/student-details";
    }

    @PutMapping("/accounts/{studentId}")
    public String updateStudentDetails(@PathVariable Long studentId,
                                       @ModelAttribute("studentDto") UserDto userDto) {


        // Update student details
        userService.updateStudentDetailsById(studentId, userDto);

        return "redirect:/admins/accounts/" + studentId + "?success";
    }

    @DeleteMapping("/accounts/{studentId}")
    public String deleteStudent(@PathVariable Long studentId) {

        // Delete student by id
        userService.deleteStudentById(studentId);

        return "redirect:/admins/accounts?deleted";
    }

}
