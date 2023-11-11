package com.system.reservation.online.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "received_date")
    private String receivedDate;

    @Column(name = "quantity")
    private Integer quantity;


}
