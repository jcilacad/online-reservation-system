package com.system.reservation.online.dto;

import jakarta.validation.constraints.*;
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
    private String name;

    @NotEmpty
    private String size;

    @DecimalMin("0.0")
    @NotNull
    private Double price;

    @NotNull
    private Integer quantity;

    private String description;

}
