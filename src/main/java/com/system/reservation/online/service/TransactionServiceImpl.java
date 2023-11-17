package com.system.reservation.online.service;

import com.system.reservation.online.dto.ReservationDto;
import com.system.reservation.online.entity.Item;
import com.system.reservation.online.entity.Transaction;
import com.system.reservation.online.entity.User;
import com.system.reservation.online.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private UserService userService;
    private ItemService itemService;

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(UserService userService,
                                  ItemService itemService,
                                  TransactionRepository transactionRepository) {
        this.userService = userService;
        this.itemService = itemService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void reserveItem(ReservationDto reservationDto, Long itemId) {

        // Get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Get email
        String email = auth.getName();

        // Find user by email
        User user = userService.findUserByEmail(email);

        // Get current item by id
        Item item = itemService.findById(itemId);


        // Get data from reservation dto
        String receivedDate = reservationDto.getPickupDate();
        Integer reserveItem = reservationDto.getReserveItem();

        // Get the current stock of item
        Integer stock = item.getQuantity();

        // Subtract the reserve item from the current stock of item
        Integer currentStock = stock - reserveItem;

        // If the difference is negative, throw an error
        if (currentStock < 0) {
            throw new RuntimeException("The stock must be greater than the reserve item");
        }

        // Set the new stock
        item.setQuantity(currentStock);

        // Get the current date
        LocalDate localDate = LocalDate.now();

        // Initialize transaction
        Transaction transaction = new Transaction(receivedDate,
                                                  reserveItem,
                                                  localDate.toString(),
                                                  " --- ",
                                                  " --- ",
                                                  user,
                                                  item);


        // Save transaction in database
        transactionRepository.save(transaction);


    }

    @Override
    public void viewTransactions() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Get current email of user
        String email = auth.getName();

        // Get the current user by email
        User user = userService.findUserByEmail(email);

        // Get list of transactions of user
        List<Transaction> transactions = transactionRepository.findByUser(user);

        
    }
}
