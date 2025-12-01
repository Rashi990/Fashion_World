package com.rash.Fashion.World.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable

public class ShopDTO {

    private String title;

    @Column(length = 1000)
    private List<String> images;

    private String description;
    private Long id;
}
