package com.system.reservation.online.service;

import com.system.reservation.online.dto.ReservationDto;
import com.system.reservation.online.entity.Transaction;

import java.util.List;

public interface TransactionService {

    void reserveItem(ReservationDto reservationDto, Long itemId);

    List<Transaction> viewTransactions ();
}
