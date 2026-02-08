package com.rash.Fashion.World.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactInformation {
    
    private String email;
    private String mobile;
    private String facebook;
}
