package com.system.reservation.online.controller;

import com.system.reservation.online.dto.ReservationDto;
import com.system.reservation.online.entity.Item;
import com.system.reservation.online.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/students")
public class StudentController {

    private ItemService itemService;

    @Autowired
    public StudentController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/dashboard")
    public String listOfItems() {
        return "student/dashboard";
    }

    @GetMapping("/items")
    public String viewItems(@RequestParam(defaultValue = "0") Integer page,
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


        return "student/items";
    }

    @GetMapping("/items/{itemId}")
    public String getItem (@PathVariable Long itemId,
                           Model model) {

        // Instantiate ReservationDto
        ReservationDto reservationDto = new ReservationDto();

        // Get item by id
        Item item = itemService.findById(itemId);

        // Create a list for reservation count, min of 1 and max of 5
        List<Integer> reservationCount = IntStream.rangeClosed(1, 5)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("reservationDto", reservationDto);
        model.addAttribute("item", item);
        model.addAttribute("reservationCount", reservationCount);

        return "student/item";
    }


    @PostMapping("/items/{itemId}")
    public String reserveItem() {

        System.out.println("reserve an item!");

        return null;
    }



}
