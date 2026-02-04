package com.rash.Fashion.World.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long id;
    private Long clothId;
    private String clothName;
    private int quantity;
    private List<String> colors;
    private Long totalPrice;
}
