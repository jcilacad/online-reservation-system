package com.system.reservation.online.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDto {


    @NotEmpty
    private String photos;

    @NotEmpty
    private String name;

    @NotEmpty
    private String size;

    @DecimalMin("0.0")
    @NotEmpty
    private Double price;

    @NotEmpty
    private Integer quantity;

    private String description;

}
