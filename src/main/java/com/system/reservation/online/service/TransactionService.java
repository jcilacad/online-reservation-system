package com.system.reservation.online.service;

import com.system.reservation.online.dto.ReservationDto;
import com.system.reservation.online.entity.Item;
import com.system.reservation.online.entity.Transaction;
import com.system.reservation.online.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    void reserveItem(ReservationDto reservationDto, Long itemId);

    Page<Transaction> findAllPaginated(Integer currentPage, Integer pageSize);

    Page<Transaction> findAllPaginatedByUserId(Long id, Integer currentPage, Integer pageSize);

    List<Transaction> viewTransactions ();
}
