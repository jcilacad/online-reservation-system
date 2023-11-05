package com.system.reservation.online.controller;

import com.system.reservation.online.dto.ItemDto;
import com.system.reservation.online.dto.UserDto;
import com.system.reservation.online.entity.Item;
import com.system.reservation.online.entity.User;
import com.system.reservation.online.service.ItemService;
import com.system.reservation.online.service.UserService;
import com.system.reservation.online.util.FileUploadUtil;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {

    UserService userService;
    ItemService itemService;

    @Autowired
    public AdminController(UserService userService,
                           ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping({"/dashboard", "/", ""})
    public String dashboard() {

        return "admin/dashboard";
    }

    @GetMapping("/accounts")
    public String accounts(Model model,
                           @RequestParam(defaultValue = "0") Integer page,
                           @RequestParam(required = false) String name) {

        // Instantiate page
        Page<User> users;

        // Instantiate userDto for form
        UserDto user = new UserDto();

        // Get the list of users

        if (name != null) {
            // If the name is present in the @RequestParam, display students that contains the name
            users = userService.findStudentByNameContaining(name, page, 10);
        } else {
            // Otherwise, display all the list of students
            users = userService.findAllPaginated(page, 10);
        }

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


    @GetMapping("/items")
    public String viewItems( @RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(name = "name", required = false) String name,
                            Model model) {

        // Get all items
        Page<Item> items;

        if (name != null) {
            // If the name is present in the @RequestParam, display students that contains the name
            items = itemService.findItemByNameContaining(name, page, 10);
        } else {
            // Otherwise, display the list of students
            items = itemService.findAllPaginated(page, 10);
        }

        model.addAttribute("items", items);
        model.addAttribute("page", page);

        return "admin/items";
    }

    @GetMapping("/items/item")
    public String addItem(Model model) {

        // Initialize itemDto
        ItemDto itemDto = new ItemDto();


        model.addAttribute("itemDto", itemDto);

        return "admin/item";
    }


    @PostMapping("/items/item")
    public String addItem(@RequestParam("image")MultipartFile multipartFile,
                          @Valid @ModelAttribute("itemDto") ItemDto itemDto,
                          BindingResult result,
                          Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("itemDto", itemDto);
            return "admin/item";
        }


        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Long id = itemService.saveItem(itemDto, fileName);

        String uploadDir = "item-photos/" + id;

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/admins/items/item?success";

    }


}
