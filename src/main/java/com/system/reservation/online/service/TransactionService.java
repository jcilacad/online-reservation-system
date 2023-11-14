package com.system.reservation.online.service;

import com.system.reservation.online.dto.ReservationDto;

public interface TransactionService {

    void reserveItem(ReservationDto reservationDto, Long itemId);
}
