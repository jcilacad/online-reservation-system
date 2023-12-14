package com.system.reservation.online.controller;

import com.system.reservation.online.dto.RemarkDto;
import com.system.reservation.online.dto.ReservationDto;
import com.system.reservation.online.entity.Item;
import com.system.reservation.online.entity.Transaction;
import com.system.reservation.online.entity.User;
import com.system.reservation.online.enums.Remark;
import com.system.reservation.online.service.ItemService;
import com.system.reservation.online.service.TransactionService;
import com.system.reservation.online.service.UserService;
import com.system.reservation.online.utils.UserExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/students")
public class StudentController {

    private ItemService itemService;
    private TransactionService transactionService;
    private UserService userService;

    @Autowired
    public StudentController(ItemService itemService,
                             TransactionService transactionService,
                             UserService userService) {
        this.itemService = itemService;
        this.transactionService = transactionService;
        this.userService = userService;
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
    public String getItem(@PathVariable Long itemId,
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
    public String reserveItem(@ModelAttribute(name = "reservationDto") ReservationDto reservationDto,
                              @PathVariable Long itemId) {

        // Reserve item
        try {
            transactionService.reserveItem(reservationDto, itemId);
            return "redirect:/students/items/" + itemId + "?success";
        } catch (Exception e) {
            return "redirect:/students/items/" + itemId + "?error";
        }

    }


    @GetMapping("/transactions")
    public String viewTransactions(@RequestParam(defaultValue = "0") Integer page,
                                   Model model) {

        // Find user by the current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Get user email
        String email = auth.getName();

        // Get the current user
        User user = userService.findUserByEmail(email);

        // Get transactions
        Page<Transaction> transactions = transactionService.findAllPaginatedByUserId(user.getId(), page, 10);

        System.out.println(transactions.getTotalPages());

        List<Remark> remarks = Arrays.asList(Remark.values());

        model.addAttribute("remarks", remarks);
        model.addAttribute("remarkDto", new RemarkDto());

        model.addAttribute("transactions", transactions);
        model.addAttribute("page", page);

        return "student/transactions";
    }

    @GetMapping("/transactions/{transactionId}")
    public String viewTransactionDetail(@PathVariable Long transactionId,
                                        Model model) {

        // Get transcation by id
        Transaction transaction = transactionService.findById(transactionId);
        // Get item by transaction
        Item item = transaction.getItem();

        model.addAttribute("transaction", transaction);
        model.addAttribute("item", item);

        return "student/transaction-details";
    }


    @PostMapping("/transactions/{transactionId}")
    public String cancelTransaction(@PathVariable Long transactionId) {

        transactionService.cancelTransaction(transactionId);
        return "redirect:/students/transactions/" + transactionId + "?cancelled";
    }

    @GetMapping("/export/excel")
    public String exportToExcel(@RequestParam(value = "remark", required = false) String remark,
                                HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue;

        List<Transaction> transactions;

        headerValue = "attachment; filename=transactions_" + remark + "_" + currentDateTime + ".xlsx";
        if (remark.equals("All")) {
            transactions = transactionService.getAllTransactions();
        } else {
            transactions = transactionService.findByRemark(remark.toString());
        }


        response.setHeader(headerKey, headerValue);

        UserExcelExporter excelExporter = new UserExcelExporter(transactions);

        excelExporter.export(response);

        return "redirect:/students/transactions";
    }


}
