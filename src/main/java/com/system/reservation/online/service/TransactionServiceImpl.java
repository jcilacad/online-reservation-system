package com.system.reservation.online.service;

import com.system.reservation.online.dto.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{

    private UserService userService;

    @Autowired
    public TransactionServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void reserveItem(ReservationDto reservationDto, Long itemId) {

        // Get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Get email
        String email = (String) auth.getPrincipal();





    }
}
