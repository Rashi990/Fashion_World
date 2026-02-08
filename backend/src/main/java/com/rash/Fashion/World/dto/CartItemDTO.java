package com.rash.Fashion.World.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemDTO {

    private Long cartItemId;
    private Long clothId;
    private String clothName;
    private int quantity;
    private List<String> colors;
    private Long unitPrice; // price of one cloth item
    private Long totalPrice; // quantity × unitPrice

}
