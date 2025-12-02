package com.rash.Fashion.World.request;

import com.rash.Fashion.World.model.Address;
import com.rash.Fashion.World.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateShopRequest {
//    This DTO is only for creating/updating a shop

//    private Long id;
    private String shopName;
    private String description;
    private Address address;
    private ContactInformation contactInformation;
    private String type;
    private String openingHours;
    private List<String> images;

}
