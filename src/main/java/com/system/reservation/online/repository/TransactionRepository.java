package com.system.reservation.online.repository;

import com.system.reservation.online.entity.Transaction;
import com.system.reservation.online.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser_Id(Long id);

}
