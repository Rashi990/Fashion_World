package com.rash.Fashion.World.dto;

import com.rash.Fashion.World.model.Address;
import com.rash.Fashion.World.model.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShopResponseDTO {

    private Long id;
    private String shopName;
    private String description;
    private String type;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
    private boolean open;
    private LocalDateTime registrationDate;
}
