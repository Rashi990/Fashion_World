package com.rash.Fashion.World.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    @JsonIgnore //whenever fetch order object, no need to fetch shop object inside the order
    @ManyToOne //One shop multiple orders
    private Shop shop;

    private Long totalAmount;
    private String orderStatus;
    private Date createdAt;

    @ManyToOne //many orders make on same address is possible, one order has only one address
    private Address deliveryAddress;

    @OneToMany
    private List<OrderItem> items;

    private int totalItem;
    private int totalPrice;

    //private Payment paymentMethod;

}
