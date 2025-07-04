package com.rash.Fashion.World.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne //multiple users order items can have same cloth
    private Cloth cloth;

    private int quantity;
    private Long totalPrice;
    private List<String> color;
}
