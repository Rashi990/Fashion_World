package com.rash.Fashion.World.request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {

    private Long clothId;
    private int quantity;
}
