package com.system.reservation.online.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {

    @AfterReturning("* com.system.reservation.online.service.TransactionServiceImpl.reserveItem(..)")
    public void recordTransactions() {

        System.out.println("Reserve an item!!!");
    }
}
