package com.rash.Fashion.World.request;

import com.rash.Fashion.World.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long shopId;
    private Address deliveryAddress;
}
