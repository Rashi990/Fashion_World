package com.rash.Fashion.World.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDTO {

    private Long cartId;
    private Long userId;
    private Long total;
    private List<CartItemDTO> items;

}
