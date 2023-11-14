package com.system.reservation.online.service;

import com.system.reservation.online.dto.ReservationDto;
import com.system.reservation.online.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{

    private UserService userService;
    private ItemService itemService;

    @Autowired
    public TransactionServiceImpl(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public void reserveItem(ReservationDto reservationDto, Long itemId) {

        // Get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Get email
        String email = (String) auth.getPrincipal();

        // Find user by email
        User user = userService.findUserByEmail(email);

        // Get current item by id
        







    }
}
