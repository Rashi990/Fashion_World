package com.rash.Fashion.World.repository;

import com.rash.Fashion.World.model.Cart;
import com.rash.Fashion.World.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

//    public Cart findByCustomerId(Long userId);
}
