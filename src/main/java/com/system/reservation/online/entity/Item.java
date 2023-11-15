package com.system.reservation.online.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "photo", nullable = false)
    private String photos;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private String size;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "item")
    private List<Transaction> transactions;

    public Item(String photos, String name, String size, Double price, Integer quantity, String description) {
        this.photos = photos;
        this.name = name;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }



    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;
        return "/item-photos/" + id + "/" + photos;
    }
}
