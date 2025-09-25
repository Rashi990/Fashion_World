package com.rash.Fashion.World.repository;

import com.rash.Fashion.World.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
