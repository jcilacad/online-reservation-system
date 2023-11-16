package com.system.reservation.online.aop;

import com.system.reservation.online.service.TransactionService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private TransactionService transactionService;

    @Autowired
    public LoggingAspect(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @After("execution(* com.system.reservation.online.service.TransactionServiceImpl.reserveItem(..))")
    public void recordTransactions() {

        System.out.println("Reserve an item!!!");
    }
}
