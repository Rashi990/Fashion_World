package com.rash.Fashion.World.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Cart cart;

    @ManyToOne //many cart items have same cloth is possible
    private Cloth cloth;

    private int quantity;
    private List<String> colors;

    private Long totalPrice;
}
