package com.system.reservation.online.service;

import com.system.reservation.online.dto.ReservationDto;
import com.system.reservation.online.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    Transaction findById(Long id);

    void reserveItem(ReservationDto reservationDto, Long itemId);

    Page<Transaction> findAllPaginated(Integer currentPage, Integer pageSize);

    Page<Transaction> findAllPaginatedByUserId(Long id, Integer currentPage, Integer pageSize);
    Page<Transaction> findAllByRemarks(String remarks, Integer currentPage, Integer pageSize);

    Page<Transaction> findAllByReceivedDate(String receivedDate, Integer currentPage, Integer pageSize);

    List<Transaction> viewTransactions ();

    void cancelTransaction(Long transactionId);

    List<Transaction> getAllTransactions();

    Page<Transaction> findByItemByNameContaining(String name, Integer currentPage, Integer pageSize);

    void approveTransaction(Long transactionId);

    void completeTransaction(Long transactionId);

    List<Transaction> findByRemark(String remark);

}
