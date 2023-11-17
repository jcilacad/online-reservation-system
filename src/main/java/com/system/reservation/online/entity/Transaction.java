package com.system.reservation.online.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "received_date")
    private String receivedDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "ordering_date")
    private String orderingDate;

    @Column(name = "approved_date")
    private String approvedDate;

    @Column(name = "cancelled_date")
    private String cancelledDate;

    @Column(name = "remarks")
    private String remarks;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Transaction(String receivedDate,
                       Integer quantity,
                       String orderingDate,
                       String approvedDate,
                       String cancelledDate,
                       String remarks,
                       User user,
                       Item item) {

        this.receivedDate = receivedDate;
        this.quantity = quantity;
        this.orderingDate = orderingDate;
        this.approvedDate = approvedDate;
        this.cancelledDate = cancelledDate;
        this.remarks = remarks;
        this.user = user;
        this.item = item;
    }
}
