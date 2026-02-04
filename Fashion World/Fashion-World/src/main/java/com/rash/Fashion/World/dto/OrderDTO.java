package com.rash.Fashion.World.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private Long userId;
    private Long shopId;

    private String orderStatus;
    private Date createdAt;

    private Long totalPrice;
    private int totalItem;

    private List<OrderItemDTO> items;

}
