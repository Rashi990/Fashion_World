package com.rash.Fashion.World.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ClothResponseDTO {

    private Long id;
    private String clothName;
    private String description;
    private Long price;

    private String categoryName;

    private List<String> images;
    private boolean available;

    private boolean isMale;
    private boolean isFemale;

    private LocalDateTime creationDate;
}
